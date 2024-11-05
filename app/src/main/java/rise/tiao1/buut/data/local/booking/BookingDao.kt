package rise.tiao1.buut.data.local.booking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: LocalBooking)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBookings(bookings: List<LocalBooking>)

    @Query("SELECT * FROM booking WHERE userId = :userId")
    suspend fun getBookingsByUserId(userId: String): List<LocalBooking>
}