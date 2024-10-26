package rise.tiao1.buut.domain.user.useCases

import android.content.SharedPreferences
import com.auth0.android.authentication.storage.CredentialsManager
import rise.tiao1.buut.data.UserRepository
import rise.tiao1.buut.domain.user.User
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val credentialsManager: CredentialsManager,
    private val sharedPreferences: SharedPreferences
) {

    suspend operator fun invoke(user: User?){
        if (user != null) {
            userRepository.deleteUser(user)
            sharedPreferences.edit().clear().apply()
            credentialsManager.clearCredentials()
        }
    }
}