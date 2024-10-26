package rise.tiao1.buut.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * Dagger/Hilt Module that provides coroutine dispatchers for different contexts.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

/**
 * Dagger/Hilt Module that provides coroutine dispatchers for different contexts.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

/**
 * Dagger/Hilt Module providing coroutine dispatchers for the application.
 * These dispatchers can be injected using Dagger/Hilt for different coroutine contexts.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    /**
     * Provides the main dispatcher for UI-related tasks.
     */
    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    /**
     * Provides the I/O dispatcher for performing background tasks.
     */
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
