package rise.tiao1.buut.domain.user.useCases

import android.util.Log
import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.domain.user.User
import java.lang.IllegalStateException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(token: String): User? {
        Log.d("auth context", "in de useCase")
        return userRepository.getUser(token)
    }
}