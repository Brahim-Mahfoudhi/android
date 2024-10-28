package rise.tiao1.buut.domain.booking

import rise.tiao1.buut.data.local.booking.LocalBooking
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.utils.toLocalDateTime
import java.time.LocalDateTime

data class Booking(
    val numberOfAdults: Int,
    val numberOfChildren: Int,
    val date: LocalDateTime,
    val boat: String? = null,
    val battery: String? = null,
    val boatComments: List<String>?,
    val batteryComments: List<String>?
)

fun User.toLocalUser(): LocalUser {
    return LocalUser(
        id = "fg",
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}

fun BookingDTO.toBooking(): Booking{
    return Booking(
        numberOfAdults = this.numberOfAdults,
        numberOfChildren = this.numberOfChildren,
        date = this.date,
        boat = this.boat?.name,
        battery = this.battery?.name,
        boatComments = this.boat?.comments,
        batteryComments = this.battery?.comments
    )
}

fun LocalBooking.toBooking(): Booking {
    return Booking(
        numberOfAdults = this.numberOfAdults,
        numberOfChildren = this.numberOfChildren,
        date = this.date.toLocalDateTime(),
        boat = this.boat,
        battery = this.battery,
        boatComments = null,
        batteryComments = null
    )
}