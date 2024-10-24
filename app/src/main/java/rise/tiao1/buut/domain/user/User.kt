package rise.tiao1.buut.domain.user

data class User(
    val id : String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val token: String? = null,
    val isAuthenticated: Boolean
)





