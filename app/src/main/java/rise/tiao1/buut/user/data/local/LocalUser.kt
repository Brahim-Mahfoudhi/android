package rise.tiao1.buut.user.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a local user entity stored in the Room database.
 *
 * @property id Unique identifier for the user.
 * @property token Authentication token associated with the user.
 * @property name Name of the user.
 * @property email Email address of the user.
 * @property emailVerified String indicating whether the user's email is verified.
 * @property picture URL or path to the user's profile picture.
 * @property updatedAt Timestamp indicating when the user's information was last updated.
 */
@Entity(tableName = "user")
data class LocalUser(
    @PrimaryKey
    @ColumnInfo
    val id: String,

    @ColumnInfo
    val token: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val email: String,

    @ColumnInfo
    val emailVerified: String,

    @ColumnInfo
    val picture: String,

    @ColumnInfo
    val updatedAt: String
)
