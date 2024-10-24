package rise.tiao1.buut.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.OutlinedTextFieldComponent
import rise.tiao1.buut.presentation.components.PasswordTextFieldComponent
import rise.tiao1.buut.utils.RegistrationFieldKey

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onValueUpdate: (input: String, field: RegistrationFieldKey) -> Unit,
    login: () -> Unit,
    onRegisterClick: () -> Unit,
){

    Log.d("AUTHTEST", "loginscreen bereikt")
    Box {
    Image(
        painter = painterResource(R.drawable.buut_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        {
            Image(
                painter = painterResource(R.drawable.buut_logo),
                contentDescription = "BUUT Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }

        Column {
            OutlinedTextFieldComponent(
                value = state.username,
                onValueChanged = {onValueUpdate(it, RegistrationFieldKey.EMAIL)},
                errorMessage = state.errorMessage,
                label = R.string.email_label,
                onFocusLost = {}
            )
            Spacer(modifier = Modifier.heightIn(16.dp))
            PasswordTextFieldComponent(
                value = state.password,
                onValueChanged = {onValueUpdate(it, RegistrationFieldKey.PASSWORD)},
                errorMessage = state.errorMessage,
                label = R.string.password,
                onFocusLost = {}
            )
            Spacer(modifier = Modifier.heightIn(32.dp))
            ButtonComponent(
                label = R.string.log_in_button,
                onClick = { login() },
            )
            Spacer(modifier = Modifier.heightIn(12.dp))
            Row {
                Text(
                    text = stringResource(R.string.no_account_yet),
                    color = Color.White
                )
                Text(
                    text= stringResource(R.string.register_here),
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }
    }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        LoginScreen(LoginScreenState(, false, false ), {}, {})
    }
}

 */






