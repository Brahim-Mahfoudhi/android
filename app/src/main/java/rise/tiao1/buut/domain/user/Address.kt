package rise.tiao1.buut.domain.user

import rise.tiao1.buut.utils.StreetType


data class Address(
    val street: StreetType,
    val houseNumber: String,
    val box: String
)
