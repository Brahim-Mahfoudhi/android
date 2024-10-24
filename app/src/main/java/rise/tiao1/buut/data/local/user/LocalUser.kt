package rise.tiao1.buut.data.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class LocalUser(
    @PrimaryKey()
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val firstName: String,
    @ColumnInfo
    val lastName: String,
    @ColumnInfo
    val email: String,
    @ColumnInfo
    val isAuthenticated: Boolean
)
