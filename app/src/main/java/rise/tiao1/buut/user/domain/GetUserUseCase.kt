package rise.tiao1.buut.user.domain

import java.lang.IllegalStateException
import javax.inject.Inject

/**
 * Use case class for retrieving a user.
 *
 * This class is responsible for handling the business logic associated with retrieving a user.
 *
 * @property getAllUsersUseCase Use case for getting a list of all users.
 * @constructor Creates an instance of [GetUserUseCase].
 */
class GetUserUseCase @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) {

    /**
     * Retrieves a user.
     *
     * @return The user.
     * @throws IllegalStateException if there are no users in the database.
     */
    suspend operator fun invoke(): User {
        val users = getAllUsersUseCase()
        if (users.isEmpty()) {
            throw IllegalStateException("No users in database")
        }
        return users[0]
    }
}
