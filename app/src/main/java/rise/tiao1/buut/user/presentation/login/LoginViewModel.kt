package rise.tiao1.buut.user.presentation.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import rise.tiao1.buut.R
import rise.tiao1.buut.di.MainDispatcher
import rise.tiao1.buut.user.domain.DeleteUserUseCase
import rise.tiao1.buut.user.domain.GetUserUseCase
import rise.tiao1.buut.user.domain.InsertUserUseCase
import rise.tiao1.buut.user.domain.User
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
    ): ViewModel() {

    private val _state = mutableStateOf(LoginScreenState(user = User(), userIsAuthenticated = false, appJustLaunched = true))
    val state : State<LoginScreenState> get() = _state

    private lateinit var account: Auth0
    private lateinit var context: Context

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _state.value = _state.value.copy(
                user = User(),
                userIsAuthenticated = false,
                appJustLaunched = false
            )
        }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getUserIfNeeded() {
       viewModelScope.launch(errorHandler + dispatcher) {
           try {
               val user = getUserUseCase()
               if (user.id.isNotEmpty()) {
                   _state.value = _state.value.copy(user = user, userIsAuthenticated = true, appJustLaunched = false)
               } else {
                   deleteUserUseCase(user)
                   _state.value = _state.value.copy(user = User(), userIsAuthenticated = false, appJustLaunched = false)
               }
           } catch (e: Exception){
               _state.value = _state.value.copy(user = User(),userIsAuthenticated = false, appJustLaunched = false)
           }
       }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
    }


    fun login() {
        WebAuthProvider
            .login(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    // The user either pressed the “Cancel” button
                    // on the Universal Login screen or something
                    // unusual happened.
                    print("Error occurred in login(): $error")
                }
                override fun onSuccess(result: Credentials) {
                    // The user successfully logged in.
                    val idToken = result.idToken
                    val user = User(idToken)
                    viewModelScope.launch(errorHandler) { insertUserUseCase(user) }
                    _state.value = _state.value.copy(user = user, userIsAuthenticated = true, appJustLaunched = false)
                }
            })
    }

    fun logout() {
        _state.value = _state.value.copy(user = User(), userIsAuthenticated = false, appJustLaunched = false)
    }
}
