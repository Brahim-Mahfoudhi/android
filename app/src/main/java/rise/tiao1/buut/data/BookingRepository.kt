package rise.tiao1.buut.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rise.tiao1.buut.data.local.booking.BookingDao
import rise.tiao1.buut.data.remote.booking.BookingApiService
import rise.tiao1.buut.data.remote.booking.toLocalBooking
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.toBooking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val bookingDao: BookingDao,
    private val apiService: BookingApiService
) {

    suspend fun getAllBookings(): List<Booking> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.getAllBookings().map { it.toBooking() }
        }

    suspend fun getAllBookingsFromUser(userId: String): List<Booking> {
        return DummyContent.getDummyBookings().filter { it.userId == userId }.map { it.toBooking() }
    }

    /*
        withContext(Dispatchers.IO) {
            try {
                refreshCache(userId)
            } catch (e: Exception) {
                throw Exception("We can not load the bookings at this moment in time.")
            }
            return@withContext bookingDao.getBookingsByUserId(userId).map { it.toBooking() }
        }*/



    private suspend fun refreshCache(userId: String) {
        val remoteBookings = apiService
            .getAllBookings()
        bookingDao.insertAllBookings(remoteBookings.map {
            it.toLocalBooking(userId)
        })
    }
}