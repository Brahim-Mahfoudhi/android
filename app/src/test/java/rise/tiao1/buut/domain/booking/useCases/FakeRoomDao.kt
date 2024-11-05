package rise.tiao1.buut.domain.booking.useCases

import rise.tiao1.buut.data.local.booking.BookingDao
import rise.tiao1.buut.data.local.booking.LocalBooking
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.local.user.UserDao

class FakeRoomDao: BookingDao {
    private var bookings = HashMap<Int, LocalBooking>()

    override suspend fun insertBooking(booking: LocalBooking) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllBookings(bookings: List<LocalBooking>) {
        bookings.forEach { this.bookings[bookings.indexOf(it)] = it }
        /*TODO aanpassen wanneer api id's van boekingen meegeven*/
    }

    override suspend fun getBookingsByUserId(userId: String): List<LocalBooking> {
        return bookings.values.toList().filter { it.userId == userId }
    }
}

class FakeUserRoomDao: UserDao {
    private var users = HashMap<Int, LocalUser>()

    override suspend fun insertUser(user: LocalUser) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): LocalUser? {
        return users.values.toList().first { it.id == id }
    }

    override suspend fun updateUser(localUser: LocalUser) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: LocalUser) {
        TODO("Not yet implemented")
    }

}