package rise.tiao1.buut.api


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import rise.tiao1.buut.user.domain.dto.RegistrationDTO
import rise.tiao1.buut.user.domain.dto.UserOutPutDTO


interface UserApiService {
    @POST("authentication/register")
    suspend fun registerUser(@Body data: RegistrationDTO): Response<UserOutPutDTO>
}