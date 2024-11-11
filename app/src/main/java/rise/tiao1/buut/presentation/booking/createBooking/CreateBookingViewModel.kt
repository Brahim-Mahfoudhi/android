package rise.tiao1.buut.presentation.booking.createBooking

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import rise.tiao1.buut.data.di.MainDispatcher
import rise.tiao1.buut.domain.booking.useCases.GetSelectableDatesUseCase
import rise.tiao1.buut.domain.booking.useCases.GetSelectableTimeSlotsUseCase
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateBookingViewModel @Inject constructor(
    private val getSelectableDatesUseCase: GetSelectableDatesUseCase,
    private val getSelectableTimeSlotsUseCase: GetSelectableTimeSlotsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    @OptIn(ExperimentalMaterial3Api::class)
    private val _state = mutableStateOf(CreateBookingScreenState())
    val state: State<CreateBookingScreenState>
        get() = _state

    init {
        getSelectableDates(
            LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onReadyForUpdate() {
        _state.value = state.value.copy(isUpdated = false)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun getSelectableDates(input: Long) {
        _state.value = state.value.copy(datesAreLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                val selectableDates = getSelectableDatesUseCase(input)

                val updatedDatePickerState = DatePickerState(
                    initialDisplayMode = DisplayMode.Picker,
                    selectableDates = selectableDates,
                    initialSelectedDateMillis = null,
                    yearRange = (LocalDate.now().year..LocalDate.now().plusYears(1L).year),
                    initialDisplayedMonthMillis = input,
                    locale = Locale.US
                )

                _state.value = state.value.copy(
                    datePickerState = updatedDatePickerState,
                    isUpdated = true,
                    datesAreLoading = false,
                )

            } catch (e: Exception) {
                _state.value =
                    state.value.copy(datesAreLoading = false, getFreeDatesError = e.message)

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun getSelectableTimeslots(input: Long) {
        _state.value = state.value.copy(timeslotsAreLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                val selectableTimeslots = getSelectableTimeSlotsUseCase(input)
                _state.value = state.value.copy(
                    selectableTimeSlots = selectableTimeslots,
                    timeslotsAreLoading = false,
                )

            } catch (e: Exception) {
                _state.value =
                    state.value.copy(timeslotsAreLoading = false, getFreeDatesError = e.message)
            }

        }
    }

    fun updateSelectedDate(input: Long?) {
        if (input != null) {
            getSelectableTimeslots(input)
        }
    }

}