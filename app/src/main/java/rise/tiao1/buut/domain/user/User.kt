package rise.tiao1.buut.domain.user

import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.user.dto.UserDTO
import rise.tiao1.buut.utils.toLocalDateTime
import java.time.LocalDateTime

data class User(
    val id : String? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String? = null,
    val phone: String? = null,
    val dateOfBirth: LocalDateTime?= null,
    val address: Address ?=null
)

fun User.toLocalUser(): LocalUser {
    return LocalUser(
        id = this.id.toString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        address = this.address,
        phone = this.phone,
        dateOfBirth = this.dateOfBirth.toString()
    )
}

fun User.toUserDTO(): UserDTO {
    return UserDTO(
        firstName = this.firstName,
        lastName = this.lastName,
        address = this.address?.toAddressDTO(),
        phone = this.phone,
        email = this.email,
        password = this.password,
        dateOfBirth = this.dateOfBirth.toString()
    )
}




