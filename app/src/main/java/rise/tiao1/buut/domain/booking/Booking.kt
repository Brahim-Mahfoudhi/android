package rise.tiao1.buut.domain.booking

import rise.tiao1.buut.data.local.booking.LocalBooking
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.utils.toLocalDateTimeFromApiString
import java.time.LocalDateTime

data class Booking(
    val id: String,
    val date: LocalDateTime,
    val boat: String? = null,
    val battery: String? = null
)

fun BookingDTO.toBooking(): Booking{
    return Booking(
        id = this.id ?: "",
        date = this.date.toLocalDateTimeFromApiString(),
        boat = this.boat?.name,
        battery = this.battery?.name,
    )
}

fun LocalBooking.toBooking(): Booking {
    return Booking(
        id = this.id,
        date = this.date.toLocalDateTimeFromApiString(),
        boat = this.boat,
        battery = this.battery,
    )
}