package rise.tiao1.buut.data.remote.booking

import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import rise.tiao1.buut.data.local.booking.LocalBooking
import rise.tiao1.buut.utils.toDateString
import java.time.LocalDateTime

data class BookingDTO(
    @SerializedName("r_countAdults")
    val numberOfAdults: Int,
    @SerializedName("r_countChildren")
    val numberOfChildren: Int,
    @SerializedName("r_bookingDate")
    val date: LocalDateTime,
    val boat: BoatDTO? = null,
    val battery: BatteryDTO? = null,
    val userId: String? = null
)

fun BookingDTO.toLocalBooking(userId: String): LocalBooking{
    return LocalBooking(
        numberOfAdults = this.numberOfAdults,
        numberOfChildren = this.numberOfChildren,
        date = this.date.toDateString(),
        boat = this.boat?.name,
        battery = this.battery?.name,
        userId = userId
    )
}
