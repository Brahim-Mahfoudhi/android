package rise.tiao1.buut.data.remote.user

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import rise.tiao1.buut.data.remote.user.dto.RegistrationDTO


interface UserApiService {
    @POST("api/User")
    suspend fun registerUser(@Body data: RegistrationDTO): Response<Boolean>

    @GET("api/User/{id}")
    suspend fun getUserById(@Path("id", encoded = false) id: String): RemoteUser

}