package rise.tiao1.buut.presentation.register

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.AutoCompleteTextFieldComponent
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.CheckboxComponent
import rise.tiao1.buut.presentation.components.DatePickerComponent
import rise.tiao1.buut.presentation.components.ErrorMessageContainer
import rise.tiao1.buut.presentation.components.MainBackgroundImage
import rise.tiao1.buut.presentation.components.OutlinedTextFieldComponent
import rise.tiao1.buut.presentation.components.PasswordTextFieldComponent
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.InputKeys
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.utils.UiLayout

@Composable
fun RegistrationScreen(
    state: RegistrationScreenState,
    onValueChanged: (input: String, field: String) -> Unit,
    onCheckedChanged: (input: Boolean, field: String) -> Unit,
    onValidate: (field: String) -> Unit,
    onSubmitClick: () -> Unit = {},
    onRegistrationSuccessDismissed: () -> Unit = {},
    uiLayout: UiLayout = UiLayout.PORTRAIT_SMALL
) {
    val scrollState = rememberScrollState()


    Box(modifier = Modifier.fillMaxSize()) {
        MainBackgroundImage()

        if (uiLayout == UiLayout.PORTRAIT_SMALL || uiLayout == UiLayout.PORTRAIT_MEDIUM)
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UserDetails(state, onValueChanged, onValidate)
                Spacer(modifier = Modifier.heightIn(5.dp))
                AddressDetails(state, onValueChanged, onValidate)
                Spacer(modifier = Modifier.heightIn(5.dp))
                Passwords(state, onValueChanged, onValidate)
                Spacer(modifier = Modifier.heightIn(5.dp))
                CheckboxesAndButton(state, onCheckedChanged, onSubmitClick)
            }
        }

        if (uiLayout == UiLayout.PORTRAIT_EXPANDED
            || uiLayout == UiLayout.LANDSCAPE_SMALL
            || uiLayout == UiLayout.LANDSCAPE_MEDIUM
            || uiLayout == UiLayout.LANDSCAPE_EXPANDED)
            {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
                    .verticalScroll(scrollState),
                horizontalArrangement = Arrangement.Center,
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    UserDetails(state, onValueChanged, onValidate)
                    Passwords(state, onValueChanged, onValidate)
                }
                Spacer(modifier = Modifier.widthIn(5.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    AddressDetails(state, onValueChanged, onValidate)
                    CheckboxesAndButton(state, onCheckedChanged, onSubmitClick)
                }
            }
        }
    }



    if (state.registrationSuccess) {
        AlertDialog(
            modifier = Modifier.testTag("RegistrationSuccessModal"),
            onDismissRequest = onRegistrationSuccessDismissed,
            title = { Text(stringResource(R.string.registration_succesful)) },
            text = { Text(stringResource(R.string.account_created)) },
            confirmButton = {
                Button(
                    modifier = Modifier.testTag("RegistrationSuccessModalButton"),
                    onClick = onRegistrationSuccessDismissed
                ) {
                    Text(stringResource(R.string.close_label))
                }
            }
        )
    }
}

@Composable
fun UserDetails(
    state: RegistrationScreenState,
    onValueChanged: (input: String, field: String) -> Unit,
    onValidate: (field: String) -> Unit,
) {
    Column {
        OutlinedTextFieldComponent(
            value = state.firstName,
            onValueChanged = { onValueChanged(it, InputKeys.FIRST_NAME) },
            onFocusLost = { onValidate(InputKeys.FIRST_NAME) },
            isError = state.firstNameError != null,
            errorMessage = state.firstNameError?.asString(),
            label = R.string.firstName,
        )

        Spacer(modifier = Modifier.heightIn(5.dp))

        OutlinedTextFieldComponent(
            value = state.lastName,
            onValueChanged = { onValueChanged(it, InputKeys.LAST_NAME) },
            onFocusLost = { onValidate(InputKeys.LAST_NAME) },
            isError = state.lastNameError != null,
            errorMessage = state.lastNameError?.asString(),
            label = R.string.last_name,
        )

        Spacer(modifier = Modifier.heightIn(5.dp))

        DatePickerComponent(
            value = state.dateOfBirth,
            onDatePicked = { onValueChanged(it, InputKeys.DATE_OF_BIRTH) },
            onFocusLost = { onValidate(InputKeys.DATE_OF_BIRTH) },
            isError = state.dateOfBirthError != null,
            errorMessage = state.dateOfBirthError?.asString(),
            label = R.string.date_of_birth,
        )

        Spacer(modifier = Modifier.heightIn(5.dp))

        OutlinedTextFieldComponent(
            value = state.email,
            onValueChanged = { onValueChanged(it, InputKeys.EMAIL) },
            onFocusLost = { onValidate(InputKeys.EMAIL) },
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
            label = R.string.email_label,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )
    }
}

@Composable
fun AddressDetails(
    state: RegistrationScreenState,
    onValueChanged: (input: String, field: String) -> Unit,
    onValidate: (field: String) -> Unit,
) {
    Column  {
        AutoCompleteTextFieldComponent(
            value = state.street,
            onValueChanged = { onValueChanged(it, InputKeys.STREET) },
            onFocusLost = { onValidate(InputKeys.STREET) },
            isError = state.streetError != null,
            errorMessage = state.streetError?.asString(),
            label = R.string.street,
            optionList = StreetType.entries.map { it.streetName },
        )

        Spacer(modifier = Modifier.heightIn(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                OutlinedTextFieldComponent(

                    value = state.houseNumber,
                    onValueChanged = { onValueChanged(it, InputKeys.HOUSE_NUMBER) },
                    onFocusLost = { onValidate(InputKeys.HOUSE_NUMBER) },
                    isError = state.houseNumberError != null,
                    errorMessage = state.houseNumberError?.asString(),
                    label = R.string.house_number,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextFieldComponent(
                    value = state.box,
                    onValueChanged = { onValueChanged(it, InputKeys.BOX) },
                    onFocusLost = { onValidate(InputKeys.BOX) },
                    label = R.string.box,
                )
            }
        }

        Spacer(modifier = Modifier.heightIn(5.dp))

        OutlinedTextFieldComponent(
            value = state.phone,
            onValueChanged = { onValueChanged(it, InputKeys.PHONE) },
            onFocusLost = { onValidate(InputKeys.PHONE) },
            isError = state.phoneError != null,
            errorMessage = state.phoneError?.asString(),
            label = R.string.phone,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
        )
    }
}

@Composable
fun Passwords(
    state: RegistrationScreenState,
    onValueChanged: (input: String, field: String) -> Unit,
    onValidate: (field: String) -> Unit
) {
    Column {
        PasswordTextFieldComponent(
            value = state.password,
            onValueChanged = { onValueChanged(it, InputKeys.PASSWORD) },
            onFocusLost = { onValidate(InputKeys.PASSWORD) },
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.asString(),
            label = R.string.password,
        )

        Spacer(modifier = Modifier.heightIn(5.dp))

        PasswordTextFieldComponent(
            value = state.repeatedPassword,
            onValueChanged = { onValueChanged(it, InputKeys.REPEATED_PASSWORD) },
            onFocusLost = { onValidate(InputKeys.REPEATED_PASSWORD) },
            isError = state.repeatedPasswordError != null,
            errorMessage = state.repeatedPasswordError?.asString(),
            label = R.string.repeated_password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
        )
    }
}


@Composable
fun CheckboxesAndButton(
    state: RegistrationScreenState,
    onCheckedChanged: (input: Boolean, field: String) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column {
        CheckboxComponent(
            value = state.acceptedTermsOfUsage,
            onChecked = { onCheckedChanged(it, InputKeys.TERMS) },
            errorMessage = state.termsError?.asString(),
            leadingText = R.string.accept,
            label = R.string.terms_of_usage,
            url = R.string.tos_url
        )

        CheckboxComponent(
            value = state.acceptedPrivacyConditions,
            onChecked = { onCheckedChanged(it, InputKeys.PRIVACY) },
            errorMessage = state.privacyError?.asString(),
            leadingText = R.string.accept,
            label = R.string.privacy_policy,
            url = R.string.privacy_url
        )

        Spacer(modifier = Modifier.heightIn(5.dp))
        ErrorMessageContainer(errorMessage = state.apiError)
        Spacer(modifier = Modifier.heightIn(5.dp))

        ButtonComponent(
            isLoading = state.isLoading,
            label = R.string.register_button,
            onClick = onSubmitClick
        )
    }

}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp", uiMode = Configuration.ORIENTATION_PORTRAIT)
@Composable
fun RegisterPortraitSmallPreview() {
    AppTheme {
        RegistrationScreen(
            RegistrationScreenState(),
            { _, _ -> {} },
            { _, _ -> {} },
            { _ -> {} },
            uiLayout = UiLayout.PORTRAIT_SMALL
        )
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 300, uiMode = Configuration.ORIENTATION_LANDSCAPE)
@Composable
fun RegisterLandscapeSmallPreview() {
    AppTheme {
        RegistrationScreen(
            RegistrationScreenState(),
            { _, _ -> {} },
            { _, _ -> {} },
            { _ -> {} },
            uiLayout = UiLayout.LANDSCAPE_SMALL
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", uiMode = Configuration.ORIENTATION_PORTRAIT)
@Composable
fun RegisterExpandedPortraitPreview() {
    AppTheme {
        RegistrationScreen(
            RegistrationScreenState(),
            { _, _ -> {} },
            { _, _ -> {} },
            { _ -> {} },
            uiLayout = UiLayout.PORTRAIT_EXPANDED
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240", uiMode = Configuration.ORIENTATION_LANDSCAPE)
@Composable
fun RegisterExpandedLandscapePreview() {
    AppTheme {
        RegistrationScreen(
            RegistrationScreenState(),
            { _, _ -> {} },
            { _, _ -> {} },
            { _ -> {} },
            uiLayout = UiLayout.LANDSCAPE_MEDIUM
        )
    }
}