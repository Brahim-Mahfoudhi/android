package rise.tiao1.buut.presentation


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import rise.tiao1.buut.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.auth0.android.Auth0
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.login.LoginScreen
import rise.tiao1.buut.presentation.login.LoginViewModel
import rise.tiao1.buut.presentation.profile.ProfileScreen
import rise.tiao1.buut.presentation.profile.ProfileViewModel
import rise.tiao1.buut.presentation.register.RegisterViewModel
import rise.tiao1.buut.presentation.register.RegistrationScreen
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
        val startDestination = remember { mutableStateOf("") }
        val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            credentialsManager.getCredentials(object :
                Callback<Credentials, CredentialsManagerException> {
                override fun onFailure(error: CredentialsManagerException) {
                    startDestination.value = Route.HOME
                    isLoading.value = false
                }

                override fun onSuccess(result: Credentials) {
                    startDestination.value = Route.PROFILE
                    isLoading.value = false
                }
            })
        }

        if (isLoading.value)
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        else {
            NavHost(
                navController,
                startDestination = startDestination.value,
            ) {
                composable(route = Route.HOME) {


                    val loginViewModel: LoginViewModel = hiltViewModel()
                    Log.d("AUTHTEST", "view model succesvol gecreerd")
                    LoginScreen(
                        state = loginViewModel.state.value,
                        onValueUpdate = { input, field ->
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
                    Log.d("AUTHTEST", "in de profile route ")
                    val viewModel: ProfileViewModel = hiltViewModel()
                    Log.d("AUTHTEST", "profilemodel aangemaakt")
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
                        onValueChanged = { oldValue, field ->
                            registerViewModel.update(oldValue, field)
                        },
                        onCheckedChanged = { oldValue, field ->
                            registerViewModel.updateAcceptedAgreements(oldValue, field)
                        },
                        onFocusLost = { field ->
                            registerViewModel.validateField(field)
                        },
                        onSubmitClick = { registerViewModel.onRegisterClick() }
                    )
                }
            }
        }
    }

}


