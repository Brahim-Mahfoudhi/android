package rise.tiao1.buut.data.remote.booking

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApiService {
    @GET("api/Booking")
    suspend fun getAllBookings(): List<BookingDTO>

    @GET("api/User/{userId}/bookings")
    suspend fun getAllBookingsFromUser(@Path("userId") userId: String): List<BookingDTO>

    @GET("api/Booking/free/byDateRange")
    suspend fun getFreeTimeSlotsForDateRange(@Query("startDate") startDate: String, @Query("endDate") endDate: String): List<TimeSlotDTO>
}

