package rise.tiao1.buut.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import rise.tiao1.buut.data.BuutDao
import rise.tiao1.buut.data.BuutDb
import rise.tiao1.buut.user.domain.GetAllUsersUseCase
import rise.tiao1.buut.user.domain.GetUserUseCase
import javax.inject.Singleton

/**
 * Dagger/Hilt Module providing dependencies related to the Irrigation Zone feature.
 * This includes Room database, Retrofit setup, and other related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object BuutModule {

    /**
     * Provides the [BuutDao] for Room database operations.
     */
    @Provides
    fun provideRoomDao(database: BuutDb): BuutDao {
        return database.dao
    }

    /**
     * Provides the Room database instance using Dagger/Hilt.
     */
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): BuutDb {
        return Room.databaseBuilder(
            appContext,
            BuutDb::class.java,
            "db_buut"
        ).fallbackToDestructiveMigration().build()
    }


    /**
     * Provides the [GetUserUseCase] with a dependency on [GetAllUsersUseCase].
     */
    @Provides
    @Singleton
    fun provideGetUserUseCase(getAllUsersUseCase: GetAllUsersUseCase): GetUserUseCase {
        return GetUserUseCase(getAllUsersUseCase)
    }

}
