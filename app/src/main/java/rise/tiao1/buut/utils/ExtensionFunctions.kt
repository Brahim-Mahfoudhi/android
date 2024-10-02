package rise.tiao1.buut.utils

import rise.tiao1.buut.user.data.local.LocalUser
import rise.tiao1.buut.user.domain.User

/**
 * Extension function to convert a [User] object to a [LocalUser] object.
 *
 * This function creates a [LocalUser] object from the properties of the provided [User] object.
 * It is used for storing user data locally in the database.
 *
 * @receiver The [User] object to be converted.
 * @return The corresponding [LocalUser] object.
 */
fun User.toLocalUser(): LocalUser {
    return LocalUser(
        id = this.id,
        token = this.idToken ?: "",
        name = this.name,
        email = this.email,
        emailVerified = this.emailVerified,
        picture = this.picture,
        updatedAt = this.updatedAt
    )
}
