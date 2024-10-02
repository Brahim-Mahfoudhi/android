package rise.tiao1.buut.user.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Data class representing a partial local user entity stored in the Room database.
 *
 * This class contains only a subset of user information, typically used when a full
 * user object is not required.
 *
 * @property id Unique identifier for the user.
 * @property token Authentication token associated with the user.
 */
@Entity
data class PartialLocalUser(
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val token: String
)
