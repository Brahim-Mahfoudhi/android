package rise.tiao1.buut.data.remote.booking

import com.google.gson.annotations.SerializedName

data class TimeSlotDTO(
    @SerializedName("bookingDate")
    val date: String,
    @SerializedName("timeSlot")
    val slot: String,
    val available: Boolean
)
