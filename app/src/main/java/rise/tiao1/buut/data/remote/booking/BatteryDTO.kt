package rise.tiao1.buut.data.remote.booking

import com.google.gson.annotations.SerializedName

data class BatteryDTO(
    val name: String,
    @SerializedName("r_listComments")
    val comments: List<String>?
)
