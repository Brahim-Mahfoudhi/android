package rise.tiao1.buut.presentation.login

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rise.tiao1.buut.utils.FieldKeys
import rise.tiao1.buut.utils.FieldKeys.*
import rise.tiao1.buut.utils.SharedPreferencesKeys
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authentication: AuthenticationAPIClient,
    private val credentialsManager: CredentialsManager,
    private val sharedPreferences: SharedPreferences,
): ViewModel() {
    private val _state = mutableStateOf(LoginScreenState())
    val state : State<LoginScreenState> get() = _state


    fun update(inputValue: String, field: FieldKeys) {
        _state.value = state.value.copy(fields = state.value.fields.toMutableMap().apply{
            this[field] = inputValue
        }
        )
    }


    fun login(navigateToProfile: ()->Unit) {
        authentication.login(state.value.fields[EMAIL] ?: "", state.value.fields[PASSWORD] ?: "")
            .setAudience("https://api.rise.buut.com")
            .start(object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                }

                override fun onSuccess(result: Credentials) {
                    viewModelScope.launch {
                        Log.d("authToken", "bij succesvolle login builde ${result.accessToken}")
                        credentialsManager.saveCredentials(result)
                        sharedPreferences.edit()
                            .putString(SharedPreferencesKeys.ACCESSTOKEN, result.accessToken)
                            .putString(SharedPreferencesKeys.IDTOKEN, result.idToken)
                            .apply()
                        navigateToProfile()
                    }
                }
            })
    }


}