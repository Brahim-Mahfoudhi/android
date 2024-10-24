package rise.tiao1.buut.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.presentation.components.AutoCompleteTextFieldComponent
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.CheckboxComponent
import rise.tiao1.buut.presentation.components.DatePickerComponent
import rise.tiao1.buut.presentation.components.OutlinedTextFieldComponent
import rise.tiao1.buut.presentation.components.PasswordTextFieldComponent
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.RegistrationFieldKey

@Composable
fun RegistrationScreen(
    state: RegistrationScreenState,
    onValueChanged: (oldValue: String, field: RegistrationFieldKey) -> Unit,
    onCheckedChanged: (oldValue: Boolean, field: RegistrationFieldKey) -> Unit,
    onFocusLost : (field: RegistrationFieldKey) -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    Box {
        Image(
            painter = painterResource(R.drawable.buut_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(26.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextFieldComponent(
                value = state.firstName,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.FIRST_NAME) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.FIRST_NAME) },
                isError = state.firstNameError != null,
                errorMessage = state.firstNameError?.asString(),
                label = R.string.firstName,
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            OutlinedTextFieldComponent(
                value = state.lastName,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.LAST_NAME) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.LAST_NAME) },
                isError = state.lastNameError != null,
                errorMessage = state.lastNameError?.asString(),
                label = R.string.last_name
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            AutoCompleteTextFieldComponent(
                value = state.street?.streetName ?: "",
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.STREET) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.STREET) },
                isError = state.streetError != null,
                errorMessage = state.streetError?.asString(),
                label = R.string.street,
                optionList = StreetType.entries.map { it.streetName }
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f),
                ) {
                    OutlinedTextFieldComponent(

                        value = state.houseNumber,
                        onValueChanged = { onValueChanged(it, RegistrationFieldKey.HOUSE_NUMBER)},
                        onFocusLost = { onFocusLost(RegistrationFieldKey.HOUSE_NUMBER) },
                        isError = state.houseNumberError != null,
                        errorMessage = state.houseNumberError?.asString(),
                        label = R.string.house_number,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedTextFieldComponent(
                        value = state.box,
                        onValueChanged = { onValueChanged(it, RegistrationFieldKey.BOX) },
                        onFocusLost = { onFocusLost(RegistrationFieldKey.BOX) },
                        label = R.string.box,
                    )
                }
            }

            Spacer(modifier = Modifier.heightIn(10.dp))

            DatePickerComponent(
                value = state.dateOfBirth,
                onDatePicked = { onValueChanged(it, RegistrationFieldKey.DATE_OF_BIRTH) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.DATE_OF_BIRTH) },
                isError = state.dateOfBirthError != null,
                errorMessage = state.dateOfBirthError?.asString(),
                label = R.string.date_of_birth
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            OutlinedTextFieldComponent(
                value = state.email,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.EMAIL) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.EMAIL) },
                isError = state.emailError != null,
                errorMessage = state.emailError?.asString(),
                label = R.string.email_label,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            OutlinedTextFieldComponent(
                value = state.phone,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.PHONE) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.PHONE) },
                isError = state.phoneError != null,
                errorMessage = state.phoneError?.asString(),
                label = R.string.phone,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            PasswordTextFieldComponent(
                value = state.password,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.PASSWORD) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.PASSWORD) },
                isError = state.passwordError != null,
                errorMessage = state.passwordError?.asString(),
                label = R.string.password,
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

            PasswordTextFieldComponent(
                value = state.repeatedPassword,
                onValueChanged = { onValueChanged(it, RegistrationFieldKey.REPEATED_PASSWORD) },
                onFocusLost = { onFocusLost(RegistrationFieldKey.REPEATED_PASSWORD) },
                isError = state.repeatedPasswordError != null,
                errorMessage = state.repeatedPasswordError?.asString(),
                label = R.string.repeated_password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(modifier = Modifier.heightIn(10.dp))

                CheckboxComponent(
                    value = state.acceptedTermsOfUsage,
                    onChecked = { onCheckedChanged(it, RegistrationFieldKey.TERMS) },
                    errorMessage = state.termsError?.asString(),
                    leadingText = R.string.accept,
                    label = R.string.terms_of_usage
                )

                CheckboxComponent(
                    value = state.acceptedPrivacyConditions,
                    onChecked = { onCheckedChanged(it, RegistrationFieldKey.PRIVACY) },
                    errorMessage = state.privacyError?.asString(),
                    leadingText = R.string.accept,
                    label = R.string.privacy_policy
                )


                ButtonComponent(
                    label = R.string.register_button,
                    onClick = onSubmitClick
                )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        RegistrationScreen(RegistrationScreenState(), {_, _ -> {}}, {_, _ -> {}})
    }
}