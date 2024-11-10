package rise.tiao1.buut.presentation.booking.createBooking

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.LocalDate
import java.util.Locale

data class CreateBookingScreenState @OptIn(ExperimentalMaterial3Api::class) constructor(

    val datePickerState: DatePickerState =DatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = null,
        yearRange = (LocalDate.now().year..LocalDate.now().plusYears(1L).year),
        locale = Locale.US
    ),
    val isUpdated: Boolean = true,
    val datesAreLoading: Boolean = false,
    val getFreeDatesError: String? = ""
)

