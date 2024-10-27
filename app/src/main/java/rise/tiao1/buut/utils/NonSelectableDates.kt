package rise.tiao1.buut.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import rise.tiao1.buut.presentation.components.toMillis
import java.time.LocalDate
import java.time.LocalDateTime

const val DAYS_FROM_NOW = 2L

@OptIn(ExperimentalMaterial3Api::class)
class NonSelectableDates(private val dates: List<Long> = emptyList()): SelectableDates{
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis > LocalDateTime.now().plusDays(DAYS_FROM_NOW).toMillis() && !dates.contains(utcTimeMillis)
    }
}