package rise.tiao1.buut.data

import com.google.type.DateTime
import rise.tiao1.buut.data.remote.booking.BatteryDTO
import rise.tiao1.buut.data.remote.booking.BoatDTO
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.utils.toApiDateString
import rise.tiao1.buut.utils.toDateString
import rise.tiao1.buut.utils.toMillis
import java.time.LocalDateTime

object DummyContent {
    fun getDummyUsers() = arrayListOf(
        RemoteUser("auth0|6713adbf2d2a7c11375ac64c", "TestVoornaam1", "TestAchternaam1", "TestEmail1@hogent.be"),
        RemoteUser( "auth0|6713ad614fda04f4b9ae2156","TestVoornaam2", "TestAchternaam2", "TestEmail2@hogent.be"),
        RemoteUser("auth0|6713ad524e8a8907fbf0d57f", "TestVoornaam3", "TestAchternaam3", "TestEmail3@hogent.be")
    )

    fun getDummyBookings(userId: String? = null): List<BookingDTO> {
        var count = 0
        val bookings = ArrayList<BookingDTO>().apply {
        repeat(30) {
            add(
                BookingDTO(
                    /*id = "id${++count}",*/
                    date = getRandomDate().toApiDateString(),
                    boat = BoatDTO(name = "boot${it + 1}"),
                    battery = BatteryDTO(name = "batterij${it + 1}"),
                    userId = getRandomUserId()
                )
            )
        }
            add(
                BookingDTO(
                   /* id = "id${++count}",*/
                    date = getRandomDate().toApiDateString(),
                    boat = BoatDTO(name = "boot${55}"),
                    battery = BatteryDTO(name = "batterij${55}"),
                    userId = "auth0|6713adbf2d2a7c11375ac64c"
                )
            )
        }
        if (userId == null)
            return bookings
        else {
            return bookings.filter { it.userId == userId }
        }

    }

    private fun getRandomDate(): LocalDateTime {
        val start = LocalDateTime.now().plusDays(2)
        val end = LocalDateTime.of(2024, 12, 1, 0, 0)
        val randomDays = (0..(end.dayOfYear - start.dayOfYear)).random()
        return start.plusDays(randomDays.toLong())
    }

    private fun getRandomUserId(): String {
        val users = getDummyUsers()
        return users.random().id
    }

}