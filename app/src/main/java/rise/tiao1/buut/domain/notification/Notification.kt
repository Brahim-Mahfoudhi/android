package rise.tiao1.buut.domain.notification

import rise.tiao1.buut.data.local.notification.LocalNotification
import rise.tiao1.buut.data.remote.notification.NotificationDTO
import rise.tiao1.buut.utils.NotificationType
import java.time.LocalDateTime

data class Notification(
    val notificationId : String,
    val userId : String,
    val title : String,
    val message: String,
    val isRead : Boolean,
    val type : NotificationType,
    val createdAt : String,
    val relatedEntityId: String
)

fun NotificationDTO.toNotification(userId: String): Notification {
    return Notification(
        notificationId = this.notificationId ?: "",
        userId = userId,
        title = this.title ?: "",
        message = this.message ?: "",
        isRead = this.isRead ?: false,
        type = NotificationType.fromString(this.type ?: "") ?: NotificationType.GENERAL,
        createdAt = this.createdAt,
        relatedEntityId = this.relatedEntityId ?: ""
    )
}

fun LocalNotification.toNotification(userId: String): Notification {
    return Notification(
        notificationId = this.id,
        userId = userId,
        title = this.title?: "",
        message = this.message?: "",
        isRead = this.isRead?: false ,
        type = NotificationType.fromString(this.type?: "General") ?: NotificationType.GENERAL,
        createdAt = this.createdAt,
        relatedEntityId = this.relatedEntityId?: "")
}
