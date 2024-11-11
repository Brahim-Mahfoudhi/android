package rise.tiao1.buut.domain.booking.useCases

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.repositories.BookingRepository
import java.time.LocalDate
import java.time.ZoneId





class GetSelectableDatesUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val bookingRepository = BookingRepository(
        FakeRoomDao(),
        FakeApiService(),
        dispatcher
    )

    private val useCase = GetSelectableDatesUseCase(
        bookingRepository = bookingRepository
    )


    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun getSelectableDates_ReturnsSelectableDates() =
        scope.runTest {
            val testMonth = LocalDate.now().plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val dateInRange = LocalDate.now().plusDays(3).plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val selectableDates = useCase(testMonth)
            assert(selectableDates.isSelectableDate(dateInRange))
        }
}