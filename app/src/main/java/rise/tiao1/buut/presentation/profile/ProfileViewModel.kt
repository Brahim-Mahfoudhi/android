package rise.tiao1.buut.presentation.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import rise.tiao1.buut.R
import rise.tiao1.buut.data.di.MainDispatcher
import rise.tiao1.buut.domain.user.useCases.DeleteUserUseCase
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.utils.NavigationKeys.Route
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val credentialsManager: CredentialsManager,
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
    ): ViewModel() {
    private val _state = mutableStateOf(ProfileScreenState())
    val state : State<ProfileScreenState> get() = _state

    init{
        Log.d("AUTHTEST", "in de profileviewmodel")
        credentialsManager.getCredentials(object :
            Callback<Credentials, CredentialsManagerException> {
            override fun onFailure(error: CredentialsManagerException) {
                Log.d("AUTHTEST", "profile model on failure")
            }

            override fun onSuccess(result: Credentials) {
                Log.d("AUTHTEST", "profile model on succes")
                viewModelScope.launch {
                    val user = getUserUseCase.invoke(result.idToken)
                    _state.value = state.value.copy(user = user)
                }

            }
        })
    }


    fun logout(navigateToLogin: () -> Unit) {
       credentialsManager.clearCredentials()
        navigateToLogin()
    }

}
