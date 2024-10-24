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

    /**
     * Inserts or updates a user in the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LocalUser)

    /**
     * Retrieves all users from the database.
     */
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<LocalUser>

    /**
     * Retrieves a specific user by their ID.
     */
    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): LocalUser?

    /**
     * Updates a user based on partial information provided in [PartialLocalUser].
     */
    @Update(entity = LocalUser::class)
    suspend fun updateUser(localUser: LocalUser)



    /**
     * Deletes a user from the database.
     */
    @Delete(entity = LocalUser::class)
    suspend fun deleteUser(user: LocalUser)
}
