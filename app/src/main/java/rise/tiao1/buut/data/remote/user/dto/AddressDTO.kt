package rise.tiao1.buut.data.remote.user.dto

import com.google.gson.annotations.SerializedName
import rise.tiao1.buut.utils.StreetType

data class AddressDTO(
    @SerializedName("street")
    val street: StreetType?,
    @SerializedName("houseNumber")
    val houseNumber: String,
    @SerializedName("bus")
    val box: String,
)
