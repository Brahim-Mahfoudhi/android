package rise.tiao1.buut.domain.booking.useCases

import rise.tiao1.buut.data.BookingRepository
import rise.tiao1.buut.domain.booking.Booking
import javax.inject.Inject

class GetBookingsUseCase @Inject constructor (
    private val bookingRepository: BookingRepository,
) {
    suspend operator fun invoke(
        userId: String,
        onSuccess: (List<Booking>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val bookings = bookingRepository.getAllBookingsFromUser(userId)
            val sortedBookings = bookings.sortedBy { it.date }
            onSuccess(sortedBookings)
        } catch (e: Exception) {
            onError("Error fetching bookings: ${e.message}")
        }
    }
}