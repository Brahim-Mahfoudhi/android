package rise.tiao1.buut.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import rise.tiao1.buut.data.di.MainDispatcher
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.domain.user.useCases.LogoutUseCase
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    ): ViewModel() {
    private val _state = mutableStateOf(ProfileScreenState())
    val state : State<ProfileScreenState> get() = _state

    init{
        getUser()
    }

    fun logout(navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.invoke(state.value.user)
            navigateToLogin()
        }

    }

    private fun getUser() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            getUserUseCase.invoke(
                onSuccess = { user: User ->
                    _state.value = state.value.copy(isLoading = false, user = user)
                },
                onError = { error ->
                    _state.value = state.value.copy(
                        isLoading = false,
                        apiError = error
                    )
                }
            )

        }
    }

}
