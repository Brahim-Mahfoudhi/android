package rise.tiao1.buut.data.remote.booking

import com.google.gson.annotations.SerializedName
import com.google.type.DateTime

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
