package rise.tiao1.buut.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import rise.tiao1.buut.presentation.MainActivity
import rise.tiao1.buut.presentation.MenuScaffold
import rise.tiao1.buut.user.presentation.login.LoginScreen
import rise.tiao1.buut.user.presentation.login.LoginViewModel
import rise.tiao1.buut.user.presentation.profile.ProfileScreen
import rise.tiao1.buut.user.presentation.profile.ProfileViewModel
import rise.tiao1.buut.user.presentation.register.RegisterScreen


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun Navigation(mainActivity: MainActivity, isAppWakeUp: Boolean) {
    val navController = rememberNavController()
    val loginViewModel : LoginViewModel = hiltViewModel()
    loginViewModel.setContext(mainActivity)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isAppWakeUp) {
        if (isAppWakeUp) {
            coroutineScope.launch {
                loginViewModel.getUserIfNeeded()
                navController.navigate(NavigationKeys.Route.AUTH)
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = NavigationKeys.Route.AUTH )
    {
        composable(route= NavigationKeys.Route.AUTH) {
            LoginScreen(
                loginViewModel.state.value,
                login = {loginViewModel.login()},
                navigateToProfile = {navController.navigate(NavigationKeys.Route.PROFILE)},
                navigateToLogin = {navController.navigate(NavigationKeys.Route.AUTH)},
                navController
            )
        }

        composable(route= NavigationKeys.Route.REGISTER) {
            RegisterScreen(navController = navController)
        }


        composable(route = NavigationKeys.Route.PROFILE) {
            val viewModel : ProfileViewModel = hiltViewModel()
            viewModel.setContext(mainActivity)
            MenuScaffold(navController = navController) {
                ProfileScreen(
                    viewModel.state.value
                ) {
                    viewModel.logout {
                        loginViewModel.logout()
                        navController.navigate(NavigationKeys.Route.AUTH) }
                }
            }
        }

    }
}
