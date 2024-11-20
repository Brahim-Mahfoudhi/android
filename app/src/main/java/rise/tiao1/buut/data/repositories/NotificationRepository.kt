package rise.tiao1.buut.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import rise.tiao1.buut.data.di.IoDispatcher
import rise.tiao1.buut.data.local.notification.NotificationDao
import rise.tiao1.buut.data.remote.notification.NotificationApiService
import rise.tiao1.buut.data.remote.notification.NotificationDTO
import rise.tiao1.buut.data.remote.notification.toLocalNotification
import rise.tiao1.buut.domain.notification.Notification
import rise.tiao1.buut.domain.notification.toNotification
import rise.tiao1.buut.utils.NotificationType
import rise.tiao1.buut.utils.toApiErrorMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao,
    private val apiService: NotificationApiService,
    @IoDispatcher private val dispatcher:
    CoroutineDispatcher
) {


    suspend fun getAllNotificationsFromUser(userId: String): List<Notification>  =
        withContext(dispatcher) {
            try {
                val remoteNotifications = apiService.getAllNotificationsFromUser(userId)
                refreshCache(userId, remoteNotifications)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }

            return@withContext  notificationDao.getNotificationsByUserId(userId).map { it.toNotification(userId) }
        }

    suspend fun getAllUnreadNotificationsFromUser(userId: String): List<Notification>  =
        withContext(dispatcher) {
            try {
                val remoteNotifications = apiService.getAllUnreadNotificationsFromUser(userId)
                refreshCache(userId,remoteNotifications )
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }

            return@withContext  notificationDao.getNotificationsByUserId(userId).map { it.toNotification(userId) }
        }

    suspend fun getAllReadNotificationsFromUser(userId: String): List<Notification>  =
        withContext(dispatcher) {
            try {
                val remoteNotifications = apiService.getAllReadNotificationsFromUser(userId)
                refreshCache(userId,remoteNotifications )
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }

            return@withContext  notificationDao.getNotificationsByUserId(userId).map { it.toNotification(userId) }
        }

    suspend fun getAllUnreadNotificationsFromUserByType(userId: String, notificationType: NotificationType): List<Notification>  =
        withContext(dispatcher) {
            try {
                val remoteNotifications = apiService.getAllNotificationsFromUserByType(userId,notificationType.notificationType)
                refreshCache(userId,remoteNotifications )
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }

            return@withContext  notificationDao.getNotificationsByUserId(userId).map { it.toNotification(userId) }
        }

    suspend fun markNotificationAsRead(notificationId: String)  =
        withContext(dispatcher) {
            try {
                apiService.markNotificationAsRead(notificationId)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> { throw Exception(e.toApiErrorMessage())}
                    else -> throw Exception(e.message)
                }
            }
        }

    private suspend fun refreshCache(userId: String, remoteNotifications: List<NotificationDTO>) {
        notificationDao.insertAllNotifications(remoteNotifications.map {it.toLocalNotification(userId)})
    }
}