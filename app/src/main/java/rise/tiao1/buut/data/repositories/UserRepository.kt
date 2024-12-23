package rise.tiao1.buut.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import rise.tiao1.buut.data.di.IoDispatcher
import rise.tiao1.buut.data.local.user.UserDao
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.remote.user.dto.UserDTO
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.toLocalUser
import rise.tiao1.buut.utils.toApiErrorMessage
import rise.tiao1.buut.utils.toLocalUser
import rise.tiao1.buut.utils.toUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val apiService: UserApiService,
    @IoDispatcher private val dispatcher:
        CoroutineDispatcher
) {
    suspend fun getUser(id: String): User =
        withContext(dispatcher) {
            try {
                var localUser = dao.getUserById(id)
                if (localUser == null) {
                    val remoteUser = apiService.getUserById(id)
                    localUser = remoteUser.toLocalUser()
                    dao.insertUser(localUser)
                }
                return@withContext localUser.toUser()
            }catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }
        }

    suspend fun deleteUser(user: User) {
        withContext(dispatcher) {
            try {
                dao.deleteUser(user.toLocalUser())
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    suspend fun registerUser(userDto: UserDTO) {
        try {
            apiService.registerUser(userDto)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> { throw Exception(e.toApiErrorMessage())}
                else -> throw Exception(e.message)
            }
        }
    }
}




