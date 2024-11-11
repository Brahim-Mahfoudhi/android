package rise.tiao1.buut.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import rise.tiao1.buut.data.di.MainDispatcher
import rise.tiao1.buut.domain.booking.useCases.GetBookingsSortedByDateUseCase
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.domain.user.useCases.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getBookingsSortedByDateUseCase: GetBookingsSortedByDateUseCase,
    private val logoutUseCase: LogoutUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> get() = _state

    init {
        getUser()
    }

    fun logout(navigateToLogin: () -> Unit) {
        viewModelScope.launch(dispatcher) {
            logoutUseCase.invoke(state.value.user)
            navigateToLogin()
        }

    }

    private fun getUser() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            getUserUseCase.invoke(
                onSuccess = { user: User ->
                    _state.value = state.value.copy(isLoading = false, user = user)
                    getBookings()
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

    private fun getBookings() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch(dispatcher) {
            val bookings = getBookingsSortedByDateUseCase(
                userId = state.value.user?.id ?: ""
            )
            _state.value = state.value.copy(bookings = bookings, isLoading = false)
        }

    }
}


