package rise.tiao1.buut.domain.booking

import rise.tiao1.buut.data.remote.booking.TimeSlotDTO
import rise.tiao1.buut.utils.toLocalDateTimeFromApiString
import java.time.LocalDateTime

data class TimeSlot (
    val date: LocalDateTime,
    val slot: String,
    val available: Boolean
)

fun TimeSlotDTO.toTimeSlot() : TimeSlot {
    return TimeSlot(
        date = this.date.toLocalDateTimeFromApiString(),
        slot= this.slot,
        available = this.available
    )
}