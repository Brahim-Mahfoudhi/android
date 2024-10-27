package rise.tiao1.buut.data.remote.booking

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface bookingApiService {
    @GET("api/Booking/all")
    suspend fun getAllBookings(): List<BookingDTO>

    @GET("api/Booking/all/{userId}")
    suspend fun getAllBookingsFromUser(@Path("userId") userId: String): List<BookingDTO>
}

