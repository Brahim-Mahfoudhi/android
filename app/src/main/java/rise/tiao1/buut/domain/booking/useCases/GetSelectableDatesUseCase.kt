package rise.tiao1.buut.domain.booking.useCases
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import rise.tiao1.buut.data.repositories.BookingRepository
import rise.tiao1.buut.presentation.components.toMillis
import javax.inject.Inject

class GetSelectableDatesUseCase @Inject constructor (
    private val bookingRepository: BookingRepository,
)  {

    @OptIn(ExperimentalMaterial3Api::class)
    suspend operator fun invoke() : SelectableDates {
        val timeSlots = bookingRepository.getAvailableDays()
        val availableDays = timeSlots.filter { it.available }.distinctBy { it.date }.map { it.date.toMillis() }
        return ConfiguredSelectableDates(availableDays)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class ConfiguredSelectableDates(private val dates: List<Long> = emptyList()): SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return dates.contains(utcTimeMillis)
    }
}