package rise.tiao1.buut.domain.booking.useCases

import kotlinx.coroutines.delay
import retrofit2.Response
import rise.tiao1.buut.data.DummyContent
import rise.tiao1.buut.data.remote.booking.BookingApiService
import rise.tiao1.buut.data.remote.booking.BookingDTO
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.remote.user.dto.UserDTO

class FakeApiService: BookingApiService {
    override suspend fun getAllBookings(): List<BookingDTO> {
        delay(1000)
        return DummyContent.getDummyBookings()
    }

    override suspend fun getAllBookingsFromUser(userId: String): List<BookingDTO> {
        delay(1000)
        return DummyContent.getDummyBookings(userId)
    }
}

class FakeUSerApiService: UserApiService {

    override suspend fun registerUser(data: UserDTO): Response<Boolean> {
        delay(1000)
        return Response.success(true)
    }

    override suspend fun getUserById(id: String): RemoteUser {
        return DummyContent.getDummyUsers().first { it.id == id }
    }
}