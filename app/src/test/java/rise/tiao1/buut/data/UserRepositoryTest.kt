package rise.tiao1.buut.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Test
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.local.user.UserDao
import rise.tiao1.buut.data.remote.user.RemoteUser
import rise.tiao1.buut.data.remote.user.UserApiService
import rise.tiao1.buut.domain.user.Address
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.domain.user.toLocalUser
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.utils.toLocalUser
import rise.tiao1.buut.utils.toUser
import retrofit2.Response
import rise.tiao1.buut.data.remote.user.dto.AddressDTO
import rise.tiao1.buut.data.remote.user.dto.UserDTO

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val dao: UserDao = mockk()
    private val apiService: UserApiService = mockk()
    private val userRepository = UserRepository(dao, apiService)

    @Test
    fun getUser_userExistsInRoom_returnsUser() = scope.runTest {
        val userFromDao = getLocalUser()
        val expected = userFromDao.toUser()
        coEvery { dao.getUserById(any()) } returns userFromDao
        val result = userRepository.getUser(expected.id.toString())
        assert(result.equals(expected))
        coVerify { dao.getUserById(expected.id.toString()) }
        coVerify(exactly = 0) { apiService.getUserById(any()) }
        coVerify(exactly = 0) { dao.insertUser(any()) }
    }

    @Test
    fun getUser_userExistsInBackendButNotRoom_returnsUserAndUpdatesRoom() = scope.runTest {
        val userFromDao = null
        val userFromApi = getRemoteUser()
        val userToInsert = userFromApi.toLocalUser()
        val expected = userFromApi.toLocalUser().toUser()
        coEvery { dao.getUserById(expected.id.toString()) } returns userFromDao
        coEvery { apiService.getUserById(expected.id.toString()) } returns userFromApi
        coEvery { dao.insertUser(userToInsert) } returns Unit
        val result = userRepository.getUser(expected.id.toString())
        assert(result.equals(expected))
        coVerify { dao.getUserById(expected.id.toString()) }
        coVerify { apiService.getUserById(expected.id.toString()) }
        coVerify { dao.insertUser(userToInsert) }
    }


    @Test
    fun getUser_userDoesNotExistInRoomAndBackend_returnsEmptyUser() = scope.runTest {
        val userFromDao = null
        val userFromApi = RemoteUser(null.toString(), null.toString(), null.toString(), null.toString())
        val userToInsert = userFromApi.toLocalUser()
        val expected = userFromApi.toLocalUser().toUser()
        coEvery { dao.getUserById(expected.id.toString()) } returns userFromDao
        coEvery { apiService.getUserById(expected.id.toString()) } returns userFromApi
        coEvery { dao.insertUser(userToInsert) } returns Unit
        val result = userRepository.getUser(expected.id.toString())
        assert(result.equals(expected))
        coVerify { dao.getUserById(expected.id.toString()) }
        coVerify { apiService.getUserById(expected.id.toString()) }
        coVerify { dao.insertUser(userToInsert) }
    }

    @Test
    fun deleteUser_deletesUserFromRoom() = scope.runTest {
        val user = getUser()
        coEvery { dao.deleteUser(user.toLocalUser()) } returns Unit
        userRepository.deleteUser(user)
        coVerify { dao.deleteUser(user.toLocalUser()) }
    }

    @Test
    fun registerUser_succesful_returnsTrue() = scope.runTest {
        val userDto = getUserDto()
        val response = Response.success(true)
        coEvery { apiService.registerUser(userDto) } returns response
        val result = userRepository.registerUser(userDto)
        assert(result == true)
        coVerify { apiService.registerUser(userDto) }
    }

    @Test
    fun registerUser_unsuccesful_returnsFalse() = scope.runTest {
        val userDto = getUserDto()
        val response = Response.error<Boolean>(400, ResponseBody.create(null, "Error"))
        coEvery { apiService.registerUser(userDto) } returns response
        val result = userRepository.registerUser(userDto)
        assert(result == false)
        coVerify { apiService.registerUser(userDto) }
    }

    fun getUser() : User {
        return User(
            id = "fg",
            firstName = "TestFirstName",
            lastName = "TestLastName",
            email = "TestEmail",
            password = "TestPassword",
            phone = "TestPhone",
            dateOfBirth = "TestDateOfBirth",
            address = Address(StreetType.AFRIKALAAN, "TestHouseNumber", "TestBox")
        )
    }

    fun getLocalUser(): LocalUser {
        return LocalUser(
            id = "fg",
            firstName = "TestFirstName",
            lastName = "TestLastName",
            email = "TestEmail")
    }

    fun getRemoteUser(): RemoteUser{
        return RemoteUser(
            id = "fg",
            firstName = "TestFirstName",
            lastName = "TestLastName",
            email = "TestEmail")
    }

    fun getUserDto() : UserDTO {
        return UserDTO(
            firstName = "TestFirstName",
            lastName = "TestLastName",
            email = "TestEmail",
            password = "TestPassword",
            phone = "TestPhone",
            dateOfBirth = "TestDateOfBirth",
            address = getAddressDto()
        )

    }

    fun getAddressDto() : AddressDTO {
        return AddressDTO(StreetType.AFRIKALAAN, "TestHuisnummer", "TestBox")
    }

}