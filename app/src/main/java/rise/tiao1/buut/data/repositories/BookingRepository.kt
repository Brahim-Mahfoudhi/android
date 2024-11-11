package rise.tiao1.buut.data.repositories

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.di.IoDispatcher
import rise.tiao1.buut.data.local.booking.BookingDao
import rise.tiao1.buut.data.remote.booking.BookingApiService
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.remote.booking.toLocalBooking
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.TimeSlot
import rise.tiao1.buut.domain.booking.toBooking
import rise.tiao1.buut.domain.booking.toTimeSlot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val bookingDao: BookingDao,
    private val apiService: BookingApiService,
    @IoDispatcher private val dispatcher:
        CoroutineDispatcher
) {
    suspend fun getAllBookingsFromUser(userId: String): List<Booking>  =
        withContext(dispatcher) {
            try {
                refreshCache(userId)
            } catch (e: Exception) {
                throw Exception("We can not load the bookings at this moment in time.")
            }

            return@withContext  bookingDao.getBookingsByUserId(userId).map { it.toBooking() }
    }

    private suspend fun refreshCache(userId: String) {
        val remoteBookings = apiService
            .getAllBookingsFromUser(userId)
        bookingDao.insertAllBookings(remoteBookings.map {
            it.toLocalBooking(userId)
        })
    }

    suspend fun getFreeTimeSlotsForDateRange(startDate: String, endDate: String): List<TimeSlot> =
        withContext(dispatcher) {
        try {
            val remoteTimeSlots = apiService.getFreeTimeSlotsForDateRange(startDate, endDate)
            return@withContext remoteTimeSlots.map { it.toTimeSlot() }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}