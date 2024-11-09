package rise.tiao1.buut.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.di.IoDispatcher
import rise.tiao1.buut.data.local.booking.BookingDao
import rise.tiao1.buut.data.remote.booking.BookingApiService
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.remote.booking.toLocalBooking
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.toBooking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val bookingDao: BookingDao,
    private val apiService: BookingApiService,
    @IoDispatcher private val dispatcher:
        CoroutineDispatcher
) {

    suspend fun getAllBookings(): List<Booking> =
        withContext(dispatcher) {
            lateinit var bookings: List<BookingDTO>
            try {
                bookings =  apiService.getAllBookings()
            } catch (e: Exception) {
                throw Exception("Something went wrong")
            }
            return@withContext bookings.map { it.toBooking() }
        }

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
}