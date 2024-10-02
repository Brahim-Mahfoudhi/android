package rise.tiao1.buut.user.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.BuutDao
import rise.tiao1.buut.user.domain.User
import rise.tiao1.buut.utils.toLocalUser
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for handling user-related data operations.
 *
 * @property buutDao Data Access Object for interacting with user data in the database.
 */
@Singleton
class UserRepository @Inject constructor(private val buutDao: BuutDao) {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id Unique identifier of the user.
     * @return [User] object representing the user with the specified ID.
     */
    suspend fun getUser(id: String): User {
        return withContext(Dispatchers.IO) {
            return@withContext User(buutDao.getUserById(id).token)
        }
    }

    /**
     * Deletes a user from the repository.
     *
     * @param user [User] object to be deleted.
     */
    suspend fun deleteUser(user: User): Unit {
        withContext(Dispatchers.IO) {
            buutDao.deleteUser(user.toLocalUser())
        }
    }

    /**
     * Inserts a new user into the repository.
     *
     * @param user [User] object to be inserted.
     */
    suspend fun insertUser(user: User): Unit {
        withContext(Dispatchers.IO) {
            buutDao.insertUser(user.toLocalUser())
        }
    }

    /**
     * Retrieves a list of all users stored in the repository.
     *
     * @return List of [User] objects representing all users in the repository.
     */
    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            return@withContext buutDao.getAllUsers().map { User(it.token) }
        }
    }
}
