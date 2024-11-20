package rise.tiao1.buut.data.remote.notification

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import rise.tiao1.buut.utils.NotificationType

interface NotificationApiService {

    @GET("api/Notification/user/{userId}")
    suspend fun getAllNotificationsFromUser(@Path("userId") userId: String, @Query("language") language: String = "en"): List<NotificationDTO>

    @GET("api/Notification/user/{userId}/unread")
    suspend fun getAllUnreadNotificationsFromUser(@Path("userId") userId: String, @Query("language") language: String = "en"): List<NotificationDTO>

    @GET("api/Notification/user/{userId}/read")
    suspend fun getAllReadNotificationsFromUser(@Path("userId") userId: String, @Query("language") language: String = "en"): List<NotificationDTO>

    @GET("api/Notification/user/{userId}/type/{type}")
    suspend fun getAllNotificationsFromUserByType(@Path("userId") userId: String, @Path("type") notificationType: String, @Query("language") language: String = "en"): List<NotificationDTO>

    @PATCH("api/Notification/{notificationId}/read")
    suspend fun markNotificationAsRead(@Path("notificationId") notificationId: String)
}

