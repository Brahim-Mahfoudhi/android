package rise.tiao1.buut.user.presentation.register

import kotlinx.coroutines.delay
import retrofit2.Response
import rise.tiao1.buut.data.DummyContent
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.data.remote.user.dto.UserDTO

class FakeUserApiService : UserApiService {
    override suspend fun registerUser(data: UserDTO): Response<Boolean> {
        val response = Response.success(true)
        delay(500)
        return response
    }

    override suspend fun getUserById(id: String): RemoteUser {
        delay(500)
        return DummyContent.getDummyUsers().find { dummyUser -> dummyUser.id == id }!!
    };
}
