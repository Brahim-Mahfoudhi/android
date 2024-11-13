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

fun TimeSlot.addTime() : TimeSlot {

    val startTime = TimeSlots.entries.first{it.slot == this.slot}.startTime
    val date = this.date.withHour(startTime)

    return TimeSlot(
        date = date,
        slot= this.slot,
        available = this.available
    )
}

enum class TimeSlots (val slot: String, val startTime: Int, val endTime: Int){
    MORNING("Morning", 10, 12),
    AFTERNOON("Afternoon", 14, 16),
    EVENING("Evening", 17, 19)
}