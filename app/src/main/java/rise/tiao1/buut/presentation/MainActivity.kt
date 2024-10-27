package rise.tiao1.buut.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auth0.android.authentication.storage.CredentialsManager
import dagger.hilt.android.AndroidEntryPoint
import rise.tiao1.buut.presentation.booking.BookingScreen
import rise.tiao1.buut.presentation.login.LoginScreen
import rise.tiao1.buut.presentation.login.LoginViewModel
import rise.tiao1.buut.presentation.profile.ProfileScreen
import rise.tiao1.buut.presentation.profile.ProfileViewModel
import rise.tiao1.buut.presentation.register.RegistrationViewModel
import rise.tiao1.buut.presentation.register.RegistrationScreen
import rise.tiao1.buut.presentation.booking.BookingViewModel
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.NavigationKeys.Route
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var credentialsManager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                BuutApp()
            }
        }
    }


    @Composable
    fun BuutApp() {
        val navController = rememberNavController()

            NavHost(
                navController,
                startDestination =  setStartingPage()
            ) {
                composable(route = Route.HOME) {
                    val loginViewModel: LoginViewModel = hiltViewModel()
                    LoginScreen(
                        state = loginViewModel.state.value,
                        onValueUpdate = {input, field: String ->
                            loginViewModel.update(input, field)
                        },
                        login = {
                            loginViewModel.login {
                                navController.navigate(Route.PROFILE)
                            }
                        },
                        onRegisterClick = {
                            navController.navigate(Route.REGISTER)
                        },
                        onValidate = {input, field: String ->
                            loginViewModel.validate(input, field)
                        }
                    )
                }
                composable(route = Route.PROFILE) {
                    val viewModel: ProfileViewModel = hiltViewModel()
                    ProfileScreen(
                        state = viewModel.state.value,
                        logout = {
                            viewModel.logout {
                                navController.navigate(Route.HOME)
                            }
                        },
                        toReservationPage = {navController.navigate(Route.RESERVATION)}
                    )
                }
                composable(route = Route.REGISTER) {
                    val registrationViewModel: RegistrationViewModel = hiltViewModel()
                    RegistrationScreen(
                        state = registrationViewModel.state.value,
                        onValueChanged = { input: String, field :String->
                            registrationViewModel.update(input, field)
                        },
                        onCheckedChanged = { input: Boolean, field: String ->
                            registrationViewModel.update(input, field)
                        },
                        onValidate = { field : String ->
                            registrationViewModel.validate(field)
                        },
                        onSubmitClick = { registrationViewModel.onRegisterClick() }
                    )
                }
                composable(route = Route.RESERVATION) {
                    val bookingViewModel: BookingViewModel = hiltViewModel()
                    BookingScreen (
                        state = bookingViewModel.state.value,
                        onValueChanged = { input: Long? ->
                            bookingViewModel.update(input)
                        }
                    )
                }
            }
        }

   private fun setStartingPage(): String{
        if (!credentialsManager.hasValidCredentials())
            return Route.HOME
        else
            return Route.PROFILE
    }

}






