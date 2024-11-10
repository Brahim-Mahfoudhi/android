package rise.tiao1.buut.domain.booking.useCases

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import rise.tiao1.buut.data.repositories.BookingRepository
import rise.tiao1.buut.presentation.components.toMillis
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetSelectableDatesUseCase @Inject constructor (
    private val bookingRepository: BookingRepository,
)  {

    @OptIn(ExperimentalMaterial3Api::class)
    suspend operator fun invoke(
        selectedMonth: Long
    ) : SelectableDates {
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(selectedMonth), ZoneId.systemDefault())
        val firstDayOfMonth = date.withDayOfMonth(1)
        val startDate = firstDayOfMonth.format(DateTimeFormatter.ISO_DATE)
        val lastDayOfMonth = date.withDayOfMonth(date.month.length(date.year % 4 == 0))
        val endDate = lastDayOfMonth.format(DateTimeFormatter.ISO_DATE)


        val timeSlots = bookingRepository.getFreeTimeSlotsForDateRange(startDate, endDate)

        val availableDates = timeSlots.filter { it.available }.distinctBy { it.date }.map { it.date.toMillis() }


        return ConfiguredSelectableDates(availableDates)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class ConfiguredSelectableDates(private val dates: List<Long> = emptyList()): SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return dates.contains(utcTimeMillis)
    }
}