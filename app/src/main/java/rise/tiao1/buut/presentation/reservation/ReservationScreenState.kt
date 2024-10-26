package rise.tiao1.buut.presentation.reservation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import rise.tiao1.buut.utils.NonSelectableDates

data class ReservationScreenState @OptIn(ExperimentalMaterial3Api::class) constructor(

    val selectedDate: Long? = 0,
    val nonSelectableDates: SelectableDates = NonSelectableDates()
)