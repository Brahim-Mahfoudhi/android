package rise.tiao1.buut.data.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.local.BuutDb
import rise.tiao1.buut.data.local.user.UserDao
import javax.inject.Singleton


/**
 * Dagger/Hilt Module providing dependencies related to the Irrigation Zone feature.
 * This includes Room database, Retrofit setup, and other related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object BuutModule {
    private var baseUrl: String = "http://10.0.2.2:5000"

    /**
     * Provides the [BuutDao] for Room database operations.
     */
    @Provides
    fun provideRoomDao(database: BuutDb): UserDao {
        return database.dao
    }

    /**
     * Provides the Room database instance using Dagger/Hilt.
     */
    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context)
    : BuutDb {
        return Room.databaseBuilder(
            appContext,
            BuutDb::class.java,
            "db_buut"
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides the [Retrofit] for API calls.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        // Maak een instance van HttpLoggingInterceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // Stel het logging-niveau in
            setLevel(HttpLoggingInterceptor.Level.BODY) // Log alle details van de aanvragen en antwoorden
        }

        // Maak een OkHttpClient met de interceptor
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Voeg de interceptor toe aan de client
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient) // Stel de OkHttpClient in voor Retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides the [UserApiService] for REST calls in the user domain.
     */
    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideAuthenticationAPIClient(@ApplicationContext appContext: Context): AuthenticationAPIClient {
        val auth0: Auth0 = Auth0(appContext)
        return AuthenticationAPIClient(auth0)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesStorage(@ApplicationContext appContext: Context): SharedPreferencesStorage {
        return SharedPreferencesStorage(appContext)
    }

    @Singleton
    @Provides
    fun provideCredentialsManager(authentication: AuthenticationAPIClient, storage: SharedPreferencesStorage): CredentialsManager {
        return CredentialsManager(authentication, storage)
    }


}
