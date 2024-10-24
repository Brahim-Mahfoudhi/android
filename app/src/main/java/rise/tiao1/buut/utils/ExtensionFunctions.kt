package rise.tiao1.buut.utils

import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.domain.user.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun User.toLocalUser(): LocalUser {
    return LocalUser(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        isAuthenticated = this.isAuthenticated
    )
}

fun LocalUser.toUser(): User {
    return User(
        id = this.id,
        //token = this.token ?: "",
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        isAuthenticated = this.isAuthenticated
    )
}

fun String.toDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    return LocalDate.parse(this, formatter)
}

fun String .toApiDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.toDate().format(formatter)
}
