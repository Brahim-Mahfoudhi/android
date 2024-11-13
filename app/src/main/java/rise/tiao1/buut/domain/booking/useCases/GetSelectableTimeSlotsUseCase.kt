package rise.tiao1.buut.domain.booking.useCases

import rise.tiao1.buut.data.repositories.BookingRepository
import rise.tiao1.buut.domain.booking.TimeSlot
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetSelectableTimeSlotsUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
) {
    suspend operator fun invoke(input: Long
    ) : List<TimeSlot> {
        val formattedDate = Instant.ofEpochMilli(input)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(DateTimeFormatter.ISO_DATE)
        val timeSlots = bookingRepository.getFreeTimeSlotsForDateRange(formattedDate)
        return timeSlots
    }
}
