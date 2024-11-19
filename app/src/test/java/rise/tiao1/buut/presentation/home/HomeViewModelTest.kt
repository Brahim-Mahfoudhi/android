package rise.tiao1.buut.presentation.home

import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.domain.booking.useCases.GetBookingsSortedByDateUseCase
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.domain.user.useCases.LogoutUseCase

class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope()
    private val getUser : GetUserUseCase = mockk()
    private val getBookings: GetBookingsSortedByDateUseCase = mockk()
    private val logout: LogoutUseCase = mockk()

    @Test
    fun initialState_isProduced() = scope.runTest {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(initialState == HomeScreenState())
    }



    private fun getViewModel(): HomeViewModel {
        return HomeViewModel(getUser, getBookings, dispatcher)
    }
}