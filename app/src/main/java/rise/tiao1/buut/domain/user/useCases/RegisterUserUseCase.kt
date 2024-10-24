package rise.tiao1.buut.domain.user.useCases

import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.data.remote.user.dto.RegistrationDTO
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userDto : RegistrationDTO): Boolean? {
        return repository.registerUser(userDto)
    }
}