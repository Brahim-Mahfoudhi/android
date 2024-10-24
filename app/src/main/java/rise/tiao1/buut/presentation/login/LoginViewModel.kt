package rise.tiao1.buut.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import rise.tiao1.buut.utils.NavigationKeys.Route
import rise.tiao1.buut.utils.RegistrationFieldKey
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authentication: AuthenticationAPIClient,
    private val credentialsManager: CredentialsManager,
): ViewModel() {
    private val _state = mutableStateOf(LoginScreenState())
    val state : State<LoginScreenState> get() = _state


    fun update(inputValue: String, field: RegistrationFieldKey) {
        when(field) {
            RegistrationFieldKey.EMAIL -> {_state.value = state.value.copy(username = inputValue)}
            RegistrationFieldKey.PASSWORD -> {_state.value = state.value.copy(password = inputValue)}
            else -> {}
        }
    }


    fun login(navigateToProfile: ()->Unit) {
        authentication.login(state.value.username, state.value.password)
            .start(object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    // Verwerk de fout hier
                    _state.value = state.value.copy(errorMessage = error.message)
                }

                override fun onSuccess(result: Credentials) {
                    // Succesvolle login, verwerk de ontvangen credentials
                    val accessToken = result.accessToken
                    credentialsManager.saveCredentials(result)
                    _state.value = state.value.copy(errorMessage = accessToken)
                    navigateToProfile()
                }
            })
    }


}