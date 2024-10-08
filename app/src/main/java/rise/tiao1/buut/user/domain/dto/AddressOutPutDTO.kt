package rise.tiao1.buut.user.domain.dto

/**
 * Data class representing the address data sent back from the REST API.
 *
 * @property street The [String] object representing the street of the User.
 * @property houseNumber The [String] object representing the house number of the User.
 * @property addressAddition The [String] object representing addition to the address of the User.
 * @constructor Creates an instance of [AddressOutPutDTO] with the provided parameters.
 */

data class AddressOutPutDTO(
    val street: String,
    val houseNumber: String,
    val addressAddition: String,
)
