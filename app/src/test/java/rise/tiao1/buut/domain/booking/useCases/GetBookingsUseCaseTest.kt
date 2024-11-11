package rise.tiao1.buut.domain.booking.useCases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.DummyContent
import rise.tiao1.buut.data.repositories.BookingRepository
import rise.tiao1.buut.domain.booking.Booking


const val USER_ID_WITH_BOOKINGS = "auth0|6713adbf2d2a7c11375ac64c"
const val USER_ID_WITHOUT_BOOKINGS = "auth0|6713adbf2d2a7c11375ac88c"

@ExperimentalCoroutinesApi
class GetBookingsUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val bookingRepository = BookingRepository(
        FakeRoomDao(),
        FakeApiService(),
        dispatcher
    )

    private val useCase = GetBookingsSortedByDateUseCase(
        bookingRepository = bookingRepository
    )

    @Test
    fun getBookings_IsReturningBookingsSortedByDateWhenUserHasBookings() = scope.runTest {
        val bookings: List<Booking> = useCase(USER_ID_WITH_BOOKINGS)
        val sortedBookings = bookings.sortedBy { it.date }
        assert(bookings == sortedBookings)
    }

    @Test
    fun getBookings_IsReturningEmptyListWhenUserHasNoBookings() = scope.runTest {
        val bookings: List<Booking> = useCase(USER_ID_WITHOUT_BOOKINGS)
        assert(bookings.isEmpty())
    }
}