package rise.tiao1.buut.presentation.profile

import rise.tiao1.buut.domain.user.User

data class ProfileScreenState (
    val user : User? = null,
    val isLoading: Boolean = true,
    val apiError: String? = ""
)