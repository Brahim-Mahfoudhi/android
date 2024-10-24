package rise.tiao1.buut.presentation.login

data class LoginScreenState (
    val username: String = "",
    val password: String = "",
    val errorMessage: String? = "",
)