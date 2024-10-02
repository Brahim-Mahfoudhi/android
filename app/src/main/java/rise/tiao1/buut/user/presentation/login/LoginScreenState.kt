package rise.tiao1.buut.user.presentation.login

import rise.tiao1.buut.user.domain.User

/**
 * Data class representing the state of the login screen.
 *
 * This class holds information about the current user state, authentication status,
 * and any potential error that might have occurred during login.
 *
 * @property user The [User] object representing the currently logged-in user.
 * @property userIsAuthenticated A flag indicating whether the user is authenticated or not.
 * @property appJustLaunched A flag indicating whether the app has just been launched.
 * @property error An optional string representing an error message if a login error occurs. Default is null.
 * @constructor Creates an instance of [LoginScreenState] with the provided parameters.
 */
data class LoginScreenState(
    val user: User,
    val userIsAuthenticated: Boolean,
    val appJustLaunched: Boolean,
    val error: String? = null
)
