package rise.tiao1.buut.domain.user

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.remote.user.dto.AddressDTO
import rise.tiao1.buut.data.remote.user.dto.UserDTO
import rise.tiao1.buut.utils.StreetType

@ExperimentalCoroutinesApi
class UserKtTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun whenToLocalUserIsCalled_returnsCorrectLocalUser() = scope.runTest{
        val user = User("1", "test1", "test2", "test3", "test4", "test5", "test6", getAddress())
        val expected = LocalUser("fg", "test1", "test2", "test3")
        val result = user.toLocalUser()
        assert(result == expected)
    }

    @Test
    fun whenToDtoIsCalled_returnsCorrectDto() = scope.runTest{
        val user = User("1", "test1", "test2", "test3", "test4", "test5", "test6", getAddress())
        val expected = UserDTO("test1", "test2", getAddressDto(), "test5", "test3", "test4", "test6")
        val result = user.toUserDTO()
        assert(result.firstName == expected.firstName)
        assert(result.lastName == expected.lastName)
        assert(result.address?.street == expected.address?.street)
        assert(result.address?.houseNumber == expected.address?.houseNumber)
        assert(result.address?.box == expected.address?.box)
        assert(result.phone == expected.phone)
        assert(result.email == expected.email)
        assert(result.password == expected.password)
        assert(result.dateOfBirth == expected.dateOfBirth)
    }

    fun getAddress() : Address {
        return Address(StreetType.AFRIKALAAN, "1", "1")
    }

    fun getAddressDto() : AddressDTO {
        return Address(StreetType.AFRIKALAAN, "1", "1").toAddressDTO()
    }
}