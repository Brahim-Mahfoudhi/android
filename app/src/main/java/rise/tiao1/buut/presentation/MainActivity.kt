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
import rise.tiao1.buut.presentation.login.LoginScreen
import rise.tiao1.buut.presentation.login.LoginViewModel
import rise.tiao1.buut.presentation.profile.ProfileScreen
import rise.tiao1.buut.presentation.profile.ProfileViewModel
import rise.tiao1.buut.presentation.register.RegisterViewModel
import rise.tiao1.buut.presentation.register.RegistrationScreen
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.FieldKeys
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
                        onValueUpdate = {input, field: FieldKeys ->
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
                        }
                    )
                }
                composable(route = Route.REGISTER) {
                    val registerViewModel: RegisterViewModel = hiltViewModel()
                    RegistrationScreen(
                        state = registerViewModel.state.value,
                        onValueChanged = { oldValue, field :FieldKeys->
                            registerViewModel.update(oldValue, field)
                        },
                        onCheckedChanged = { oldValue, field :FieldKeys ->
                            registerViewModel.updateAcceptedAgreements(oldValue, field)
                        },
                        onFocusLost = { field : FieldKeys->
                            registerViewModel.validateField(field)
                        },
                        onSubmitClick = { registerViewModel.onRegisterClick() }
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






