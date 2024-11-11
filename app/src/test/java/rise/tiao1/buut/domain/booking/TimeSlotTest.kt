package rise.tiao1.buut.domain.booking

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.remote.booking.TimeSlotDTO
import java.time.LocalDateTime

class TimeSlotTest {
    private val scope = TestScope()

    @Test
    fun timeSlotDTOToTimeSlot_returnsCorrectTimeSlot() = scope.runTest {
        val receivedTimeSlotFromApi = getTimeSlotDTO()
        val expected = getTimeSlot()
        val result = receivedTimeSlotFromApi.toTimeSlot()
        assert(result.equals(expected))
    }

    private fun getTimeSlotDTO(): TimeSlotDTO {
        return TimeSlotDTO(
            date = "2024-12-01T00:00:00",
            slot = "Morning",
            available = true
        )
    }

    private fun getTimeSlot(): TimeSlot{
        return TimeSlot(
            date = LocalDateTime.of(2024, 12, 1, 0,0,0),
            slot= "Morning",
            available = true
        )
    }
}