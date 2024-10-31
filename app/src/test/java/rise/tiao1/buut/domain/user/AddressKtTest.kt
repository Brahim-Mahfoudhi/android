package rise.tiao1.buut.domain.user

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import rise.tiao1.buut.utils.StreetType

@ExperimentalCoroutinesApi
class AddressKtTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun toDtoIsCalled_returnsCorrectDto() = scope.runTest{
        val address = Address(StreetType.AFRIKALAAN, "1", "1")
        val dto = address.toAddressDTO()
        assert(dto.street == StreetType.AFRIKALAAN)
        assert(dto.houseNumber == "1")
        assert(dto.box == "1")
    }
}