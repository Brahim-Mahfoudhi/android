package rise.tiao1.buut.user.domain



data class Address(
    val street: StreetType,
    val houseNumber: String,
    val addressAddition: String
)
