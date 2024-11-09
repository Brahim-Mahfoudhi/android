package rise.tiao1.buut.data.remote.booking

import retrofit2.http.GET
import retrofit2.http.Path

interface BookingApiService {
    @GET("api/Booking")
    suspend fun getAllBookings(): List<BookingDTO>

    @GET("api/User/{userId}/bookings")
    suspend fun getAllBookingsFromUser(@Path("userId") userId: String): List<BookingDTO>
}

