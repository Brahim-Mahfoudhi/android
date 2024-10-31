package rise.tiao1.buut.domain.user.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText

@ExperimentalCoroutinesApi
class ValidateEmailTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Test
    fun emailIsBlank_returnsCorrectError() = scope.runTest {
        val validateEmail = ValidateEmail()
        val result = validateEmail.execute("")
        assert(result != null)
        assertEquals(UiText.StringResource(resId = R.string.email_is_blank_error).getStringId(), result?.getStringId())
    }
    
//Omdat de validator gebruik maakt van util.patterns, kunnen we deze niet volledig testen zonder die te mocken en injecten.
// Dit is bad practice omdat we moeten vertrouwen dat Java's built-in libraries werken.
}