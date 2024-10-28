package rise.tiao1.buut.data.local.booking

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.DateTime
import java.time.LocalDateTime

@Entity(tableName = "booking")
data class LocalBooking (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val numberOfAdults: Int,
    @ColumnInfo
    val numberOfChildren: Int,
    @ColumnInfo
    val date: String,
    @ColumnInfo
    val boat: String? = null,
    @ColumnInfo
    val battery: String? = null,
    @ColumnInfo
    val userId: String
)


