package rise.tiao1.buut.domain.notification.useCases

import rise.tiao1.buut.data.repositories.NotificationRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository){

    suspend operator fun invoke(userId: String) = notificationRepository.getAllNotificationsFromUser(userId)
}