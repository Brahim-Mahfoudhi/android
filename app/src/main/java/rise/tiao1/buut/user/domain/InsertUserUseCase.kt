package rise.tiao1.buut.user.domain

import rise.tiao1.buut.user.data.UserRepository
import javax.inject.Inject

/**
 * Use case class for inserting a user.
 *
 * This class is responsible for handling the business logic associated with inserting a user.
 *
 * @property userRepository Repository for user-related data operations.
 * @constructor Creates an instance of [InsertUserUseCase].
 */
class InsertUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    /**
     * Inserts a user into the repository.
     *
     * @param user The user to be inserted.
     */
    suspend operator fun invoke(user: User) {
        userRepository.insertUser(user)
    }
}
