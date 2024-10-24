package rise.tiao1.buut.data

import android.util.Log
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.local.user.UserDao
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.data.remote.user.dto.RegistrationDTO
import rise.tiao1.buut.utils.toLocalUser
import rise.tiao1.buut.utils.toUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val apiService: UserApiService
) {
    suspend fun getUser(token: String): User? =
        withContext(Dispatchers.IO) {
           Log.d("in de repo", "token = ${token}")
            val jwt = JWT(token)
            val id : String? = jwt.subject
            if (id != null) {
                val remoteUser: RemoteUser? = DummyContent.getDummyUsers().find { it.id == id }
                Log.d("in de repo", "remote user = ${remoteUser}")
                if (id != null) {
                    var localUser: LocalUser? = dao.getUserById(id)
                    if (localUser == null)
                        if (remoteUser != null) {
                            localUser = LocalUser(
                                remoteUser.id,
                                remoteUser.firstName,
                                remoteUser.lastName,
                                remoteUser.email,
                                true
                            )
                            dao.insertUser(localUser)
                        }
                }
            }
                return@withContext id?.let { dao.getUserById(it)?.toUser() }
        }

    suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            dao.deleteUser(user.toLocalUser())
        }
    }

    suspend fun registerUser(registerDto: RegistrationDTO): Boolean? {
        val response = apiService.registerUser(registerDto)
        if (response.isSuccessful)
            return response.body()
        return false
    }
}




