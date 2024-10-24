package rise.tiao1.buut.data.remote.user.dto


import com.google.gson.annotations.SerializedName
import com.google.type.DateTime
import rise.tiao1.buut.utils.StreetType
import java.time.LocalDateTime

/**
 * Data class representing the registration data sent to the REST API.
 *
 * @property firstName The [String] object representing the first name of the User.
 * @property lastName The [String] object representing the last name of the User.
 * @property telephone The [String] object representing the telephone of the User.
 * @property street The [StreetType] object representing the street of the User.
 * @property houseNumber The [String] object representing the house number of the User.
 * @property addressAddition The [String] object representing addition to the address of the User.
 * @property email The [String] object representing the email of the User.
 * @property password The [String] object representing the password of the User.
 * @property dateOfBirth The [String] object representing the date of birth of the User.
 * @constructor Creates an instance of [RegistrationDTO] with the provided parameters.
 */


data class RegistrationDTO(
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("Address")
    val address: AddressOutPutDTO,
    @SerializedName("PhoneNumber")
    val phoneNumber: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("BirthDate")
    val birthDate: String
)

data class AddressOutPutDTO(
    @SerializedName("Street")
    val street: StreetType?,
    @SerializedName("HouseNumber")
    val houseNumber: String,
    @SerializedName("Bus")
    val bus: String,
)
