package rise.tiao1.buut.presentation.profile

import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.user.User


data class ProfileScreenState (
    val user: User? = null,
    val bookings: List<Booking> = emptyList(),
    val isLoading: Boolean = false,
    val apiError: String = "",
)