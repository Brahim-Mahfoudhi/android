package rise.tiao1.buut.domain.user.useCases

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import rise.tiao1.buut.data.UserRepository


@ExperimentalCoroutinesApi
class GetUserUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val userRepository: UserRepository = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val JWT: JWT = mockk()

    @Test
    fun getUserUseCase_GeenToken_returnsFailure(): Unit = scope.runTest {
        val getUserUseCase = GetUserUseCase(userRepository, sharedPreferences)
        var result = false
        getUserUseCase(
            onSuccess = {},
            onError = { error ->
                result = true
            }
        )

        Assert.assertTrue(result)
    }

    //Happy flow kunnen we niet testen omdat de JWT decoder niet mockbaar is.
}