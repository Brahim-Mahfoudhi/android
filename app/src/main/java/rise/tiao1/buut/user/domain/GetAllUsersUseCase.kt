package rise.tiao1.buut.user.domain

import rise.tiao1.buut.user.data.UserRepository
import javax.inject.Inject

/**
 * Use case class for retrieving all users.
 *
 * This class is responsible for handling the business logic associated with retrieving a list of all users.
 *
 * @property userRepository Repository for user-related data operations.
 * @constructor Creates an instance of [GetAllUsersUseCase].
 */
class GetAllUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Retrieves a list of all users.
     *
     * @return A list of [User] objects representing all users.
     */
    suspend operator fun invoke(): List<User> {
        return userRepository.getAllUsers()
    }
}
