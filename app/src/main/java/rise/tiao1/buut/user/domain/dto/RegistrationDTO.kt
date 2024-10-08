package rise.tiao1.buut.user.domain.dto

import rise.tiao1.buut.user.domain.StreetType

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
    val firstName: String,
    val lastName: String,
    val street: StreetType?,
    val houseNumber: String,
    val addressAddition: String,
    val telephone: String,
    val email: String,
    val password: String,
    val dateOfBirth: String
)
