package rise.tiao1.buut.domain.booking

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.local.booking.LocalBooking
import rise.tiao1.buut.data.remote.booking.BatteryDTO
import rise.tiao1.buut.data.remote.booking.BoatDTO
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.utils.toApiDateString
import rise.tiao1.buut.utils.toDateString
import rise.tiao1.buut.utils.toLocalDateTimeFromApiString
import rise.tiao1.buut.utils.toTestDateString
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class BookingKtTest{
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun whenBookingDTOToBookingIsCalled_returnsCorrectBooking() = scope.runTest{
        val booking = getBookingDTO()
        val expected = getBooking()
        val result = booking.toBooking()
        assert(result.equals(expected))
    }

    @Test
    fun whenLocalBookingToBookingIsCalled_returnsCorrectBooking() = scope.runTest {
        val booking = getLocalBooking()
        val expected = getBooking()
        val result = booking.toBooking()
        assert(result.equals(expected))
    }


    fun getBooking() : Booking {
        return Booking(
            id = "1",
            date = LocalDateTime.now().toApiDateString().toLocalDateTimeFromApiString(),
            boat = "TestBoat",
            battery = "TestBattery")

    }


    fun getBookingDTO() : BookingDTO {
        return BookingDTO(
            id = "1",
            date = LocalDateTime.now().toApiDateString(),
            boat = getBoatDTO(),
            battery = getBatteryDTO())
    }

    fun getLocalBooking() : LocalBooking {
        return LocalBooking(
            id = "1",
            date = LocalDateTime.now().toApiDateString(),
            boat = "TestBoat",
            battery = "TestBattery",
            userId = "TestUserId"
        )
    }

    fun getBoatDTO() : BoatDTO {
        return BoatDTO(
            name = "TestBoat")
    }

    fun getBatteryDTO() : BatteryDTO {
        return BatteryDTO(
            name = "TestBattery")
    }
}