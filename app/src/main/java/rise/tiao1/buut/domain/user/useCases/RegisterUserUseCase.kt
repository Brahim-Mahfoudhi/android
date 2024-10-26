package rise.tiao1.buut.domain.user.useCases

import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.toUserDTO
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(
        user : User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            repository.registerUser(user.toUserDTO())
            onSuccess()
        } catch (e: Exception) {
            onError("Error registering user: ${e.message}")
        }

    }
}