package rise.tiao1.buut.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import rise.tiao1.buut.user.data.local.LocalUser
import rise.tiao1.buut.user.data.local.PartialLocalUser


/**
 * Data Access Object (DAO) interface for interacting with the Room database.
 * Defines methods for performing CRUD (Create, Read, Update, Delete) operations on entities.
 */
@Dao
interface BuutDao {

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
    suspend fun getUserById(id: String): LocalUser

    /**
     * Updates a user based on partial information provided in [PartialLocalUser].
     */
    @Update(entity = LocalUser::class)
    suspend fun updateUser(partialLocalUser: PartialLocalUser)

    /**
     * Deletes a user from the database.
     */
    @Delete(entity = LocalUser::class)
    suspend fun deleteUser(user: LocalUser)
}
