package rise.tiao1.buut.domain.user.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import rise.tiao1.buut.utils.UiText
import rise.tiao1.buut.R

@ExperimentalCoroutinesApi
class ValidatePhoneTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun phoneIsBlank_returnsCorrectError() = scope.runTest {
        val validatePhone = ValidatePhone()
        val result = validatePhone.execute("")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.invalid_phone_error).getStringId(), result?.getStringId())

    }

    @Test
    fun phoneIsInvalid_returnsCorrectError() = scope.runTest {
        val validatePhone = ValidatePhone()
        val result = validatePhone.execute("1234")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.invalid_phone_error).getStringId(), result?.getStringId())
    }

    @Test
    fun phoneIsValidLandline_returnsNull() = scope.runTest {
        val validatePhone = ValidatePhone()
        val result = validatePhone.execute("1234567890")
        assert(result == null)
    }

    @Test
    fun phoneIsValidMobile_returnsNull() = scope.runTest {
        val validatePhone = ValidatePhone()
        val result = validatePhone.execute("123456789")
        assert(result == null)
    }

}