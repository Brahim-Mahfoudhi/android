package rise.tiao1.buut.domain.user.useCases

import android.content.SharedPreferences
import android.util.Log
import com.auth0.android.jwt.JWT
import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.utils.SharedPreferencesKeys
import java.lang.IllegalStateException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
) {

    suspend operator fun invoke(): User {
        try {
            val idToken = (sharedPreferences.getString(SharedPreferencesKeys.IDTOKEN, ""))
            var id = ""
            if (!idToken.isNullOrEmpty())
                id = JWT(idToken).subject ?: ""
            Log.d("user", "id in de usecase $id")
            return userRepository.getUser(id)
        } catch (e: Exception) {
            throw Exception("Error fetching user: ${e.message}", e)
        }
    }
}