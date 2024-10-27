package rise.tiao1.buut.data.remote.booking

import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import rise.tiao1.buut.data.local.booking.LocalBooking

data class BookingDTO(
    @SerializedName("r_countAdults")
    val numberOfAdults: Int,
    @SerializedName("r_countChildren")
    val numberOfChildren: Int,
    @SerializedName("r_bookingDate")
    val date: DateTime,
    val boat: BoatDTO,
    val battery: BatteryDTO
)

fun BookingDTO.toLocalBooking(userId: String): LocalBooking{
    return LocalBooking(
        numberOfAdults = this.numberOfAdults,
        numberOfChildren = this.numberOfChildren,
        date = this.date,
        boat = this.boat.name,
        battery = this.battery.name,
        boatComments = this.boat.comments,
        batteryComments = this.battery.comments,
        userId = userId
    )
}
