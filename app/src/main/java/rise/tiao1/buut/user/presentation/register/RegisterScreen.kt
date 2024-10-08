package rise.tiao1.buut.user.presentation.register

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import rise.tiao1.buut.navigation.NavigationKeys
import rise.tiao1.buut.user.domain.StreetType
import rise.tiao1.buut.user.presentation.login.Title
import java.time.LocalDate


@SuppressLint("DefaultLocale")
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel(), navController: NavHostController){
    val uiState by viewModel.uistate
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Register")

        Spacer(modifier = Modifier.height(16.dp))

        NameInputRow(uiState, viewModel)

        EmailAndPhoneNumberRow(uiState, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        StreetSelectorDropdown(uiState, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        HouseNumberAndAddressAdditionRow(uiState, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        PasswordFields(uiState, viewModel)

        Spacer(modifier = Modifier.height(16.dp))

        DateOfBirthDropdown(
            selectedDay = viewModel.selectedDay,
            selectedMonth = viewModel.selectedMonth,
            selectedYear = viewModel.selectedYear,
            onDaySelected = { viewModel.OnDaySelected(it) },
            onMonthSelected = { viewModel.onMonthSelected(it) },
            onYearSelected = { viewModel.onYearSelected(it) },
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.height(16.dp))

        TermsAndPrivacyCheckboxes(uiState, viewModel)

        NavigationButtons(navController, viewModel, uiState)
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = NavHostController(context = LocalContext.current))
}

@Composable
private fun NameInputRow(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        TextField(
            value = uiState.firstName,
            onValueChange = {
                viewModel.firstNameTouched = true
                viewModel.onFirstNameChanged(it) },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            isError = uiState.firstNameError != null
        )

        TextField(
            value = uiState.lastName,
            onValueChange = {
                viewModel.lastNameTouched = true
                viewModel.onLastNameChanged(it) },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            isError = uiState.lastNameError != null
        )

    }

    Box(modifier = Modifier.height(16.dp)) {
        if (uiState.firstNameError != null || uiState.lastNameError != null) {
            Row {
                Text(
                    text = uiState.firstNameError ?: "",
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = uiState.lastNameError ?: "",
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun EmailAndPhoneNumberRow(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    Row {

        TextField(
            value = uiState.email,
            onValueChange = {
                viewModel.emailTouched = true
                viewModel.onEmailChanged(it) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            isError = uiState.emailError != null
        )

        TextField(
            value = uiState.telephone,
            onValueChange = {
                viewModel.phoneNumberTouched = true
                viewModel.onTelephoneChanged(it) },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    Box(modifier = Modifier.height(16.dp)) {
        if (uiState.emailError != null || uiState.telephoneError != null) {
            Row {
                Text(
                    text = uiState.emailError ?: "",
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = uiState.telephoneError ?: "",
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun StreetSelectorDropdown(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(text = uiState.street?.streetName ?: "Select a street")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            StreetType.entries.forEach { street ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.streetTouched = true
                        viewModel.onStreetSelected(street)
                        expanded = false
                    },
                    text = { Text(text = street.streetName) },
                )
            }
        }
    }
}

@Composable
private fun HouseNumberAndAddressAdditionRow(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    Row {

        TextField(
            value = uiState.houseNumber,
            onValueChange = {
                viewModel.houseNumberTouched = true
                viewModel.onHouseNumberChanged(it) },
            label = { Text("House Number") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            isError = uiState.houseNumberError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        TextField(
            value = uiState.addressAddition,
            onValueChange = { viewModel.onAddressAdditionChanged(it) },
            label = { Text("Addition (Optional)") },
            modifier = Modifier
                .weight(1f)

        )
    }

    Box(modifier = Modifier.height(16.dp)) {
        if (uiState.houseNumberError != null) {
            Row {
                Text(
                    text = uiState.houseNumberError,
                    color = Color.Red,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun PasswordFields(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    TextField(
        value = uiState.password,
        onValueChange = {
            viewModel.passwordTouched = true
            viewModel.onPasswordChanged(it) },
        label = { Text("Password") },
        isError = uiState.passwordError != null,
        visualTransformation = PasswordVisualTransformation()
    )
    if (uiState.passwordError != null) {
        Text(
            text = uiState.passwordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextField(
        value = uiState.confirmPassword,
        onValueChange = {
            viewModel.passwordConfirmTouched = true
            viewModel.onConfirmPasswordChanged(it) },
        label = { Text("Confirm Password") },
        isError = uiState.confirmPasswordError != null,
        visualTransformation = PasswordVisualTransformation()
    )
    if (uiState.confirmPasswordError != null) {
        Text(
            text = uiState.confirmPasswordError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DateOfBirthDropdown(
    selectedDay: String,
    selectedMonth: String,
    selectedYear: String,
    onDaySelected: (String) -> Unit,
    onMonthSelected: (String) -> Unit,
    onYearSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel
) {
    val days = (1..31).map { it.toString().padStart(2, '0') }
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val years = (1900..LocalDate.now().year).map { it.toString() }.reversed()

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        // Day Dropdown
        DropdownMenuField(
            label = "Day",
            options = days,
            selectedOption = selectedDay,
            onOptionSelected = onDaySelected,
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )

        // Month Dropdown
        DropdownMenuField(
            label = "Month",
            options = months,
            selectedOption = selectedMonth,
            onOptionSelected = onMonthSelected,
            modifier = Modifier.weight(2f),
            viewModel = viewModel
        )

        // Year Dropdown
        DropdownMenuField(
            label = "Year",
            options = years,
            selectedOption = selectedYear,
            onOptionSelected = onYearSelected,
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val labelText = if (selectedOption.isEmpty()) label else selectedOption

    Box(modifier = modifier) {
        OutlinedTextField(
            value = labelText,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        viewModel.dateOfBirthTouched = true
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}

@Composable
private fun TermsAndPrivacyCheckboxes(
    uiState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = uiState.hasAgreedWithTermsOfUsage,
            onCheckedChange = {
                viewModel.termsTouched = true
                viewModel.onAcceptTermsChanged(it) }
        )
        Text(text = "I accept the Terms of Usage.")
    }

    if (uiState.termsAgreementError != null) {
        Text(
            text = uiState.termsAgreementError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
    }


    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = uiState.hasAgreedWithPrivacyConditions,
            onCheckedChange = {
                viewModel.privacyTouched = true
                viewModel.onAcceptPrivacyChanged(it) }
        )
        Text(text = "I accept the Privacy Policy.")
    }

    if (uiState.privacyAgreementError != null) {
        Text(
            text = uiState.privacyAgreementError,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun NavigationButtons(
    navController: NavHostController,
    viewModel: RegisterViewModel,
    uiState: RegisterScreenState
) {
    Row {

        Button(
            onClick = { navController.navigate(route = NavigationKeys.Route.AUTH) },
        ) {
            Text(text = "Back")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { viewModel.onRegisterClick() },
            enabled = uiState.isFormValid
        ) {
            Text(text = "Register")
        }
    }
}