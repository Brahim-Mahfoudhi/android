package rise.tiao1.buut.user.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.callback.Callback
import com.auth0.android.authentication.AuthenticationException
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import rise.tiao1.buut.R
import rise.tiao1.buut.di.MainDispatcher
import rise.tiao1.buut.user.domain.DeleteUserUseCase
import rise.tiao1.buut.user.domain.GetUserUseCase
import rise.tiao1.buut.user.domain.User
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
    ): ViewModel() {


    private val _state = mutableStateOf(ProfileScreenState(user = User()))
    val state : State<ProfileScreenState> get() = _state

    private lateinit var account: Auth0
    private lateinit var context: Context

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _state.value = _state.value.copy(
                user = User(),
            )
        }
    private fun getUserIfNeeded() {
        viewModelScope.launch(errorHandler + dispatcher) {
            try {
                val user = getUserUseCase()
                _state.value = _state.value.copy(user = user)
            } catch (e: IllegalStateException){
                print(e.toString())
            }
        }
    }

    fun setContext(activityContext: Context) {
        context = activityContext
        account = Auth0(
            context.getString(R.string.com_auth0_client_id),
            context.getString(R.string.com_auth0_domain)
        )
        getUserIfNeeded()
    }


    fun logout(navigate: ()-> Unit) {
        WebAuthProvider
            .logout(account)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    // For some reason, logout failed.
                    print("Error occurred in logout(): $error")
                }
                override fun onSuccess(result: Void?) {
                    // The user successfully logged out.
                    viewModelScope.launch(errorHandler) {
                        viewModelScope.launch(errorHandler) {
                            deleteUserUseCase(_state.value.user)
                        }.join()
                        _state.value = _state.value.copy(user = User())
                        navigate()
                    }
                }
            })
    }

}
