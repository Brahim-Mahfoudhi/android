package rise.tiao1.buut.data.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.TimeSlot
import rise.tiao1.buut.domain.booking.useCases.FakeApiService
import rise.tiao1.buut.domain.booking.useCases.FakeRoomDao
import rise.tiao1.buut.domain.booking.useCases.USER_ID_WITHOUT_BOOKINGS
import rise.tiao1.buut.domain.booking.useCases.USER_ID_WITH_BOOKINGS
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class BookingRepositoryTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val dao = FakeRoomDao()
    private val service = FakeApiService()
    private val repo = BookingRepository(dao, service, dispatcher)

    @Test
    fun getBookings_IsReturningBookingsWhenUserHasBookings() = scope.runTest {
        val bookings: List<Booking> = repo.getAllBookingsFromUser(USER_ID_WITH_BOOKINGS)
        assert(bookings.isNotEmpty())
    }

    @Test
    fun getBookings_IsReturningEmptyListWhenUserHasNoBookings() = scope.runTest {
        val bookings: List<Booking> = repo.getAllBookingsFromUser(USER_ID_WITHOUT_BOOKINGS)
        assert(bookings.isEmpty())
    }

    @Test
    fun getFreeDates_returnsListOfTimeSlots() = scope.runTest {
        val timeSlots: List<TimeSlot> = repo.getFreeTimeSlotsForDateRange(
            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE),
            LocalDateTime.now().plusDays(5).format(DateTimeFormatter.ISO_DATE),
            )
        assert(timeSlots.isNotEmpty())
    }
}