package rise.tiao1.buut.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.local.user.UserDao
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.remote.user.dto.UserDTO
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.toLocalUser
import rise.tiao1.buut.utils.toLocalUser
import rise.tiao1.buut.utils.toUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val apiService: UserApiService
) {
    suspend fun getUser(id: String): User =
        withContext(Dispatchers.IO) {
            var localUser = dao.getUserById(id)
            if (localUser == null){
                val remoteUser = apiService.getUserById(id) /* DummyContent.getDummyUsers().find { it.id == id }*/
                localUser = remoteUser.toLocalUser()
                dao.insertUser(localUser)
            }
            return@withContext localUser.toUser()
        }

    suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            dao.deleteUser(user.toLocalUser())
        }
    }

    suspend fun registerUser(userDto: UserDTO): Boolean? {
        val response = apiService.registerUser(userDto)
        if (response.isSuccessful)
            return response.body()
        return false
    }
}




