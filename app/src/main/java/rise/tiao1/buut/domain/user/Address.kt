package rise.tiao1.buut.domain.user

import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.user.dto.AddressDTO
import rise.tiao1.buut.utils.StreetType


data class Address(
    val street: StreetType?,
    val houseNumber: String,
    val box: String
)

fun Address.toAddressDTO(): AddressDTO {
    return AddressDTO(
        street = this.street,
        houseNumber = this.houseNumber,
        box = this.box
    )
}
