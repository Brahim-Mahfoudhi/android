package rise.tiao1.buut.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.BuutLogo
import rise.tiao1.buut.presentation.components.ClickableTextComponent
import rise.tiao1.buut.presentation.components.ErrorMessageContainer
import rise.tiao1.buut.presentation.components.MainBackgroundImage
import rise.tiao1.buut.presentation.components.OutlinedTextFieldComponent
import rise.tiao1.buut.presentation.components.PasswordTextFieldComponent
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.InputKeys


@Composable
fun LoginScreen(
    state: LoginScreenState,
    onValueUpdate: (input: String, field: String) -> Unit,
    login: () -> Unit,
    onRegisterClick: () -> Unit,
    onValidate: (input: String, field: String) -> Unit,
    windowSize: WindowWidthSizeClass
) {

    val configuration = LocalConfiguration.current

    Box()
    {
        MainBackgroundImage()

        if (
            (windowSize == WindowWidthSizeClass.Medium || windowSize == WindowWidthSizeClass.Compact)
            && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BuutLogo()
                Spacer(modifier = Modifier.widthIn(40.dp))
                Column {
                    LoginScreenContent(
                        state, onValueUpdate, login, onRegisterClick, onValidate
                    )
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                BuutLogo()
                Spacer(modifier = Modifier.heightIn(70.dp))
                LoginScreenContent(
                    state, onValueUpdate, login, onRegisterClick, onValidate
                )
            }
        }
    }

}

@Composable
fun LoginScreenContent(
    state: LoginScreenState,
    onValueUpdate: (input: String, field: String) -> Unit,
    login: () -> Unit,
    onRegisterClick: () -> Unit,
    onValidate: (input: String, field: String) -> Unit,

    ) {
    Column(
        modifier = Modifier.width(300.dp)
    ) {
        Column()
        {
            OutlinedTextFieldComponent(
                value = state.email ?: "",
                onValueChanged = { onValueUpdate(it, InputKeys.EMAIL) },
                errorMessage = state.emailError?.asString(),
                label = R.string.email_label,
                onFocusLost = { onValidate(state.email, InputKeys.EMAIL) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            PasswordTextFieldComponent(
                value = state.password ?: "",
                onValueChanged = { onValueUpdate(it, InputKeys.PASSWORD) },
                errorMessage = state.passwordError?.asString(),
                label = R.string.password,
                onFocusLost = { onValidate(state.password, InputKeys.PASSWORD) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            ErrorMessageContainer(errorMessage = state.apiError)
        }
        Column(
        ) {
            ButtonComponent(
                label = R.string.log_in_button,
                onClick = { login() },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.heightIn(6.dp))
            ClickableTextComponent(
                leadingText = R.string.no_account_yet,
                clickableText = R.string.register_here,
                onClick = onRegisterClick
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        LoginScreen(
            LoginScreenState(),
            { _, _ -> },
            {},
            {},
            { _, _ -> },
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 300)
@Composable
fun LoginLandscapeSmallPreview() {
    AppTheme {
        LoginScreen(
            LoginScreenState(),
            { _, _ -> },
            {},
            {},
            { _, _ -> },
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun LoginExpandedPreview() {
    AppTheme {
        LoginScreen(
            LoginScreenState(),
            { _, _ -> },
            {},
            {},
            { _, _ -> },
            windowSize = WindowWidthSizeClass.Expanded
        )
    }
}








