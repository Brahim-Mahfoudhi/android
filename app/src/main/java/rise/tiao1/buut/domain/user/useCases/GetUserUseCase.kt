package rise.tiao1.buut.domain.user.useCases

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import rise.tiao1.buut.data.repositories.UserRepository
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.utils.SharedPreferencesKeys
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) {

    suspend operator fun invoke(
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val idToken = (sharedPreferences.getString(SharedPreferencesKeys.IDTOKEN, ""))
            var id = ""
            if (!idToken.isNullOrEmpty())
                id = JWT(idToken).subject ?: ""
            val user = userRepository.getUser(id)
            onSuccess(user)
        } catch (e: Exception) {
           onError("Error fetching user: ${e.message}")
        }
    }
}