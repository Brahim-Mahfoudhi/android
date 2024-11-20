package rise.tiao1.buut.utils

enum class NotificationType (val notificationType: String) {
    GENERAL ("General"),
    Alert ("Alert"),
    Reminder ("Reminder"),
    Booking ("Booking"),
    Boat ("Boat"),
    Battery ("Battery"),
    UserRegistration ("UserRegistration");

    companion object {
        fun fromString(notificationType: String): NotificationType? {
            return NotificationType.entries.find { it.notificationType.equals(notificationType, ignoreCase = true) }
        }
    }

}