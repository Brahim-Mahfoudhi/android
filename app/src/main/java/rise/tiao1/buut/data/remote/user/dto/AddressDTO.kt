package rise.tiao1.buut.data.remote.user.dto

import com.google.gson.annotations.SerializedName
import rise.tiao1.buut.utils.StreetType

data class AddressDTO(
    @SerializedName("Street")
    val street: StreetType?,
    @SerializedName("HouseNumber")
    val houseNumber: String,
    @SerializedName("Bus")
    val box: String,
)
