package rise.tiao1.buut.user.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import rise.tiao1.buut.R
import rise.tiao1.buut.navigation.NavigationKeys

@Composable
fun LoginScreen(state: LoginScreenState, login: () -> Unit, navigateToProfile: () -> Unit = {}, navigateToLogin: () -> Unit = {}, navController: NavHostController){
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.userIsAuthenticated) {
            navigateToProfile()
        }
        val title =
            if (state.appJustLaunched) {
                stringResource(R.string.initial_title)
            } else {
                stringResource(R.string.logged_out_title)
            }
        Title( text =title )
        LogButton(
            text = stringResource(R.string.log_in_button),
            onClick = { login()},
        )

        LogButton(
            text = stringResource(R.string.register_button),
            onClick = { navController.navigate(route= NavigationKeys.Route.REGISTER)},
        )
    }
}



@Composable
fun Title(text: String){
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
    )
}






@Composable
fun LogButton( text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}