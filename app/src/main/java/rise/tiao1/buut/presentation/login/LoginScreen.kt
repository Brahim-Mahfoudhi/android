package rise.tiao1.buut.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.BuutLogo
import rise.tiao1.buut.presentation.components.OutlinedTextFieldComponent
import rise.tiao1.buut.presentation.components.PasswordTextFieldComponent
import rise.tiao1.buut.presentation.components.rememberImeState
import rise.tiao1.buut.utils.FieldKeys
import rise.tiao1.buut.utils.FieldKeys.EMAIL
import rise.tiao1.buut.utils.FieldKeys.PASSWORD


@Composable
fun LoginScreen(
    state: LoginScreenState,
    onValueUpdate: (input: String, field: FieldKeys) -> Unit,
    login: () -> Unit,
    onRegisterClick: () -> Unit,
){
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

   Box(modifier = Modifier.fillMaxSize())
   {
    Image(
        painter = painterResource(R.drawable.buut_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        BuutLogo()
        Spacer(modifier = Modifier.heightIn(70.dp))
        Column {
            OutlinedTextFieldComponent(
                value = state.fields.get(EMAIL) ?: "",
                onValueChanged = { onValueUpdate(it, EMAIL) },
                errorMessage = state.errors.get(EMAIL) ?: "",
                label = R.string.email_label,
                onFocusLost = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
            )
            PasswordTextFieldComponent(
                value = state.fields.get(PASSWORD) ?: "",
                onValueChanged = { onValueUpdate(it, PASSWORD) },
                errorMessage = state.fields.get(PASSWORD) ?: "",
                label = R.string.password,
                onFocusLost = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
            )
        }
            Spacer(modifier = Modifier.heightIn(50.dp))
            Column {
                ButtonComponent(
                    label = R.string.log_in_button,
                    onClick = { login() },
                )
                Spacer(modifier = Modifier.heightIn(6.dp))
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






