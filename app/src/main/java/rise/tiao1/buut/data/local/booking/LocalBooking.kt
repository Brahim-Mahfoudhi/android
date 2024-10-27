package rise.tiao1.buut.data.local.booking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.DateTime
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.domain.booking.toBooking

@Entity(tableName = "booking")
data class LocalBooking (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val numberOfAdults: Int,
    @ColumnInfo
    val numberOfChildren: Int,
    @ColumnInfo
    val date: DateTime,
    @ColumnInfo
    val boat: String? = null,
    @ColumnInfo
    val battery: String? = null,
    @ColumnInfo
    val boatComments: List<String>? = null,
    @ColumnInfo
    val batteryComments: List<String>? = null,
    @ColumnInfo
    val userId: String
)


