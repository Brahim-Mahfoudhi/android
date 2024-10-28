package rise.tiao1.buut.data

import com.google.type.DateTime
import rise.tiao1.buut.data.remote.booking.BatteryDTO
import rise.tiao1.buut.data.remote.booking.BoatDTO
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.remote.user.RemoteUser
import java.time.LocalDateTime

object DummyContent {
    fun getDummyUsers() = arrayListOf(
        RemoteUser("auth0|6713adbf2d2a7c11375ac64c", "TestVoornaam1", "TestAchternaam1", "TestEmail1@hogent.be"),
        RemoteUser( "auth0|6713ad614fda04f4b9ae2156","TestVoornaam2", "TestAchternaam2", "TestEmail2@hogent.be"),
        RemoteUser("auth0|6713ad524e8a8907fbf0d57f", "TestVoornaam3", "TestAchternaam3", "TestEmail3@hogent.be")
    )

    fun getDummyBookings() = ArrayList<BookingDTO>().apply {
        repeat(30) {
            add(
                BookingDTO(
                    numberOfAdults = getRandomAdults(),
                    numberOfChildren = getRandomChildren(),
                    date = getRandomDate(),
                    boat = BoatDTO(name = "boot${it + 1}", comments = null),
                    battery = BatteryDTO(name = "batterij${it + 1}", comments = null),
                    userId = getRandomUserId()
                )
            )
        }
    }

    private fun getRandomAdults(): Int {
        return (1..8).random()
    }

    private fun getRandomChildren(): Int {
        return (0..6).random()
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