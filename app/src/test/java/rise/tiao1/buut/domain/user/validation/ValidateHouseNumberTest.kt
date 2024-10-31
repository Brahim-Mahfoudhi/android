package rise.tiao1.buut.domain.user.validation

import android.text.TextUtils
import android.text.TextUtils.isDigitsOnly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import rise.tiao1.buut.utils.UiText
import rise.tiao1.buut.R

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ValidateHouseNumberTest{
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun houseNumberIsBlank_returnsCorrectError() = scope.runTest {
        val validateHouseNumber = ValidateHouseNumber()
        val result = validateHouseNumber.execute("")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.invalid_house_number_error, LOWEST_POSSIBLE_HOUSE_NUMBER).getStringId(), result?.getStringId())
    }

    @Test
    fun houseNumberIsNegative_returnsCorrectError() = scope.runTest {
        val validateHouseNumber = ValidateHouseNumber()
        val result = validateHouseNumber.execute("-1")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.invalid_house_number_error, LOWEST_POSSIBLE_HOUSE_NUMBER).getStringId(), result?.getStringId())
    }

    @Test
    fun houseNumberContainsLetters_returnsCorrectError() = scope.runTest {
        val validateHouseNumber = ValidateHouseNumber()
        val result = validateHouseNumber.execute("abc")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.invalid_house_number_error, LOWEST_POSSIBLE_HOUSE_NUMBER).getStringId(), result?.getStringId())
    }

    @Test
    fun houseNumberIsNotBlank_returnsNull() = scope.runTest {
        val validateHouseNumber = ValidateHouseNumber()
        val result = validateHouseNumber.execute("1")
        assert(result == null)
    }
}