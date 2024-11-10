package rise.tiao1.buut.presentation.booking.createBooking

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rise.tiao1.buut.domain.booking.useCases.ConfiguredSelectableDates
import rise.tiao1.buut.domain.booking.useCases.GetSelectableDatesUseCase
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateBookingViewModel @Inject constructor(
    private val getSelectableDatesUseCase: GetSelectableDatesUseCase
) : ViewModel() {
    @OptIn(ExperimentalMaterial3Api::class)
    private val _state = mutableStateOf(CreateBookingScreenState())
    val state: State<CreateBookingScreenState>
        get() = _state

    init {
        getSelectableDates(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onReadyForUpdate() {
        _state.value = state.value.copy(isUpdated = false)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun getSelectableDates(input: Long) {
        _state.value = state.value.copy(datesAreLoading = true)
        viewModelScope.launch {
            try {
                val selectableDates =  getSelectableDatesUseCase(input)

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




}