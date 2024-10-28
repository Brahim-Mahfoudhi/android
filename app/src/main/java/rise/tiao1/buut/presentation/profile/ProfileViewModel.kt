package rise.tiao1.buut.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import rise.tiao1.buut.data.di.MainDispatcher
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.useCases.GetBookingsUseCase
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.domain.user.useCases.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getBookingsUseCase: GetBookingsUseCase,
    private val logoutUseCase: LogoutUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
    ): ViewModel() {
    private val _state = mutableStateOf(ProfileScreenState())
    val state : State<ProfileScreenState> get() = _state

    init{
        //getUser()
        getBookings()
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

    private fun getBookings() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch {
            Log.d("bookings", "hier komt ie")
            Log.d("bookings", "user: ${state.value.user?.id}")
                getBookingsUseCase.invoke(
                    userId = "auth0|6713adbf2d2a7c11375ac64c",

                    onSuccess = { bookings: List<Booking> ->
                        _state.value = state.value.copy(isLoading = false, bookings = bookings)
                        Log.d("bookings", "bookings: $bookings")
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


