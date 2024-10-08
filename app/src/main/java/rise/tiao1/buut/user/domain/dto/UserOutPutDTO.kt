package rise.tiao1.buut.user.domain.dto

/**
 * Data class representing the user data sent back from the REST API.
 *
 * @property firstName The [String] object representing the first name of the User.
 * @property lastName The [String] object representing the last name of the User.
 * @property telephone The [String] object representing the telephone of the User.
 * @property address The [AddressOutPutDTO] object representing the address of the User.
 * @property email The [String] object representing the email of the User.
 * @constructor Creates an instance of [UserOutPutDTO] with the provided parameters.
 */

data class UserOutPutDTO(
    val firstName: String,
    val lastName: String,
    val address: AddressOutPutDTO,
    val telephone: String,
    val email: String,
)
