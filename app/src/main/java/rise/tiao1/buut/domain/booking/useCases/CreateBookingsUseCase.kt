package rise.tiao1.buut.domain.booking.useCases

import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.repositories.BookingRepository
import rise.tiao1.buut.domain.booking.TimeSlot
import rise.tiao1.buut.domain.user.useCases.GetUserUseCase
import rise.tiao1.buut.utils.toApiDateString
import javax.inject.Inject

class CreateBookingsUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val getUserUseCase: GetUserUseCase
) {
    suspend operator fun invoke(timeSlot: TimeSlot) {
        val userId = getUserUseCase().id
        val newBooking = BookingDTO(
            date = timeSlot.date.toApiDateString(),
            userId = userId
        )
        bookingRepository.createBooking(newBooking)
    }
}