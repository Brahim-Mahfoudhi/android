//package rise.tiao1.buut.presentation.booking.createBooking
//
//import androidx.compose.material3.ExperimentalMaterial3Api
//import io.mockk.coEvery
//import io.mockk.mockk
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.TestScope
//import kotlinx.coroutines.test.runTest
//import org.junit.Test
//import rise.tiao1.buut.domain.booking.useCases.ConfiguredSelectableDates
//import rise.tiao1.buut.domain.booking.useCases.GetSelectableDatesUseCase
//
//class CreateBookingViewModelTest {
//    private val dispatcher = StandardTestDispatcher()
//    private val scope = TestScope()
//    private val getSelectableDatesUseCase : GetSelectableDatesUseCase = mockk()
//
//    @Test
//    fun initialState_isProduced() = scope.runTest {
//        val viewModel = getViewModel()
//        val initialState = viewModel.state.value
//        assert(initialState.datesAreLoading)
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Test
//    fun getSelectablesDates_startCall_updatesLoadingState() = scope.runTest {
//        coEvery { getSelectableDatesUseCase(any()) } coAnswers {
//            kotlinx.coroutines.delay(1000L)
//            ConfiguredSelectableDates(emptyList())
//        }
//
//        val viewModel = getViewModel()
//        viewModel.getSelectableDates(1630454400000L)
//
//        assert(viewModel.state.value.datesAreLoading)
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Test
//    fun getSelectableDates_failedCall_updatesErrorState() = scope.runTest {
//        val errorMessage = "Failed to fetch dates"
//        coEvery { getSelectableDatesUseCase(any()) } throws Exception(errorMessage)
//
//        val viewModel = getViewModel()
//        viewModel.getSelectableDates(1630454400000L)
//
//        dispatcher.scheduler.advanceUntilIdle()
//
//        assert(viewModel.state.value.getFreeDatesError == errorMessage)
//        assert(!viewModel.state.value.datesAreLoading)
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Test
//    fun getSelectableDates_succesfulCall_updatesStateSelectableDatesValue() = scope.runTest {
//        val selectableDate = 1630454400000L
//        val selectableDates = ConfiguredSelectableDates(listOf(selectableDate))
//
//        coEvery { getSelectableDatesUseCase(any()) } returns selectableDates
//
//        val viewModel = getViewModel()
//        viewModel.getSelectableDates(selectableDate)
//
//        dispatcher.scheduler.advanceUntilIdle()
//
//        assert(viewModel.state.value.datePickerState.selectableDates == selectableDates)
//    }
//
//    private fun getViewModel(): CreateBookingViewModel {
//        return CreateBookingViewModel(getSelectableDatesUseCase, dispatcher)
//    }
//
//}
//
