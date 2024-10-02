package rise.tiao1.buut.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rise.tiao1.buut.user.data.local.LocalUser

/**
 * Room Database class for the application.
 * Defines the entities and version of the database, and provides access to the DAO.
 */
@Database(
    entities = [LocalUser::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class BuutDb : RoomDatabase() {

    /**
     * Provides access to the Data Access Object (DAO) for database operations.
     */
    abstract val dao: BuutDao
}
