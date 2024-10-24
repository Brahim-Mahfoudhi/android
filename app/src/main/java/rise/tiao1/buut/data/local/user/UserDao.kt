package rise.tiao1.buut.data.local.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


/**
 * Data Access Object (DAO) interface for interacting with the Room database.
 * Defines methods for performing CRUD (Create, Read, Update, Delete) operations on entities.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LocalUser)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): LocalUser?

    @Update(entity = LocalUser::class)
    suspend fun updateUser(localUser: LocalUser)

    @Delete(entity = LocalUser::class)
    suspend fun deleteUser(user: LocalUser)
}
