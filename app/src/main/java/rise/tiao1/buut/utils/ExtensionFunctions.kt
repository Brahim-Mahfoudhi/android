package rise.tiao1.buut.utils

import androidx.compose.material3.Text
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.domain.user.User
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun RemoteUser.toLocalUser(): LocalUser {
    return LocalUser(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}

fun LocalUser.toUser(): User {
    return User(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}

fun String.toDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    return LocalDate.parse(this, formatter)
}

fun LocalDateTime.toTestDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    return this.format(formatter)
}

fun String.toApiDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.toDate().format(formatter)
}

fun LocalDateTime.toApiDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return this.format(formatter)
}

fun LocalDateTime.toMillis(): Long{
    return this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toDateString(): String {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault()).toDateString()
}

fun LocalDateTime.toDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    val dayOfWeek = this.dayOfWeek
    val formattedDayOfWeek = dayOfWeek
        .getDisplayName(TextStyle.FULL, Locale.getDefault())
        .lowercase()
        .replaceFirstChar { it.uppercase() }
    return "$formattedDayOfWeek  ${formatter.format(this)}"
}

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    return LocalDateTime.parse(this, formatter)
}

fun String.toLocalDateTimeFromApiString(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return LocalDateTime.parse(this, formatter)
}

