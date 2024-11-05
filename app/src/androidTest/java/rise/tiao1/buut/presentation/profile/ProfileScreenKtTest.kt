package rise.tiao1.buut.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.user.Address
import rise.tiao1.buut.domain.user.User
import rise.tiao1.buut.presentation.login.LoginScreen
import rise.tiao1.buut.presentation.login.LoginScreenState
import rise.tiao1.buut.utils.NavigationKeys
import rise.tiao1.buut.utils.NavigationKeys.Route
import rise.tiao1.buut.utils.StreetType


class ProfileScreenKtTest{
    @get:Rule
    val rule: ComposeContentTestRule =
        createComposeRule()
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    var navControllerState by mutableStateOf<NavController?>(null)
    val welkomstText = rule.onNodeWithText(context.getString(R.string.initial_title))
    val loadingIndicator = rule.onNodeWithTag("LoadingIndicator")
    val logoutButton = rule.onNodeWithText(context.getString(R.string.log_out_button))
    val nameLabel = rule.onNodeWithText(context.getString(R.string.name_label))
    val emailLabel = rule.onNodeWithText(context.getString(R.string.email_label))
    val nameRow = rule.onNodeWithText(getUser().firstName)
    val emailRow = rule.onNodeWithText(getUser().email)
    val errorMessage = rule.onNodeWithText("TestError")

    @Test
    fun loginScreen_loadingState_displayCorrectly() {
        rule.setContent {
            ProfileScreen(
                state = getLoadingState(),
                logout = {},
                toReservationPage = { },
            )
        }

        welkomstText.assertIsDisplayed()
        loadingIndicator.assertIsDisplayed()
        nameLabel.isNotDisplayed()
        emailLabel.isNotDisplayed()
        nameRow.isNotDisplayed()
        emailRow.isNotDisplayed()
        errorMessage.isNotDisplayed()
        logoutButton.assertIsDisplayed()
    }

    @Test
    fun loginScreen_loadedState_displayCorrectly() {
        rule.setContent {
            ProfileScreen(
                state = getState(),
                logout = {},
                toReservationPage = { },
            )
        }

        welkomstText.assertIsDisplayed()
        loadingIndicator.isNotDisplayed()
        nameLabel.assertIsDisplayed()
        emailLabel.assertIsDisplayed()
        nameRow.assertIsDisplayed()
        emailRow.assertIsDisplayed()
        errorMessage.isNotDisplayed()
        logoutButton.assertIsDisplayed()
    }

    @Test
    fun loginScreen_errorState_displayCorrectly() {
        rule.setContent {
            ProfileScreen(
                state = getErrorState(),
                logout = {},
                toReservationPage = { },
            )
        }
        welkomstText.assertIsDisplayed()
        loadingIndicator.isNotDisplayed()
        nameLabel.isNotDisplayed()
        emailLabel.isNotDisplayed()
        nameRow.isNotDisplayed()
        emailRow.isNotDisplayed()
        errorMessage.assertIsDisplayed()
        logoutButton.assertIsDisplayed()
    }

    @Test
    fun profileScreen_logoutButtonPressed_routesToHome() {
        rule.setContent {
            val navController = rememberNavController()
            navControllerState = navController
            NavHost(navController = navController, startDestination = NavigationKeys.Route.PROFILE) {
                composable(NavigationKeys.Route.HOME)  { LoginScreen(
                    state = LoginScreenState(),
                    onValueUpdate = { _, _ -> },
                    login = { },
                    onRegisterClick = {},
                    onValidate = { _, _ -> }
                ) }
                composable(NavigationKeys.Route.PROFILE) { ProfileScreen (
                    state = getState(),
                    logout = {navController.navigate(Route.HOME)},
                    toReservationPage = {}
                ) }
            }
        }
        val navController = navControllerState!!
        logoutButton.performClick()
        assert(navController.currentDestination?.route == NavigationKeys.Route.HOME)
    }


    fun getLoadingState() : ProfileScreenState {
        return ProfileScreenState(
            user = null,
            isLoading = true,
            apiError = "")
}
    fun getState() : ProfileScreenState {
        return ProfileScreenState(
            user = getUser(),
            isLoading = false,
            apiError = ""
        )
    }

    fun getErrorState() : ProfileScreenState {
        return ProfileScreenState(
            user = null,
            isLoading = false,
            apiError = "TestError"
        )
    }

    fun getUser() : User {
        return User(
            id = "TestId",
            firstName = "TestFirstName",
            lastName = "TestLastName",
            email = "TestEmail",
            password = "TestPassword",
            phone = "TestPhone",
            dateOfBirth = "TestDateOfBirth",
            address = getAddress()
        )
    }

    fun getAddress() : Address {
        return Address(StreetType.AFRIKALAAN, "TestHuisnummer", "TestBox")
    }
}