package rise.tiao1.buut.user.presentation.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.runtime.LaunchedEffect
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
import java.util.Calendar


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

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) { TextField(
            value = uiState.firstName,
            onValueChange = { viewModel.onFirstNameChanged(it) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().weight(1f).padding(end = 8.dp),
            isError = uiState.firstNameError != null
        )

            TextField(
                value = uiState.lastName,
                onValueChange = { viewModel.onLastNameChanged(it) },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth().weight(1f),
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

        Row {

            TextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().weight(1f).padding(end = 8.dp),
                isError = uiState.emailError != null
            )

            TextField(
                value = uiState.telephone,
                onValueChange = { viewModel.onTelephoneChanged(it) },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth().weight(1f),
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

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown to select a street
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
                            viewModel.onStreetSelected(street)
                            expanded = false
                        },
                        text = { Text(text = street.streetName) },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            TextField(
            value = uiState.houseNumber,
            onValueChange = { viewModel.onHouseNumberChanged(it) },
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
                        text = uiState.houseNumberError ?: "",
                        color = Color.Red,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            isError = uiState.passwordError != null,
            visualTransformation = PasswordVisualTransformation()
        )
        if (uiState.passwordError != null) {
            Text(text = uiState.passwordError!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChanged(it) },
            label = { Text("Confirm Password") },
            isError = uiState.confirmPasswordError != null,
            visualTransformation = PasswordVisualTransformation()
        )
        if (uiState.confirmPasswordError != null) {
            Text(text = uiState.confirmPasswordError!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        DateOfBirthField(
            dateOfBirth = uiState.dateOfBirth,
            onDateOfBirthSelected = { selectedDate ->
                viewModel.onDateOfBirthSelected(selectedDate)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.hasAgreedWithTermsOfUsage,
                onCheckedChange = { viewModel.onAcceptTermsChanged(it) }
            )
            Text(text = "I accept the Terms of Usage.")
        }

        if (uiState.termsAgreementError != null) {
            Text(text = uiState.termsAgreementError!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.hasAgreedWithPrivacyConditions,
                onCheckedChange = { viewModel.onAcceptPrivacyChanged(it) }
            )
            Text(text = "I accept the Privacy Policy.")
        }

        if (uiState.privacyAgreementError != null) {
            Text(text = uiState.privacyAgreementError!!, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            Button(
                onClick = { navController.navigate(route= NavigationKeys.Route.AUTH)},
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
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = NavHostController(context = LocalContext.current))
}

@Composable
fun DateOfBirthField(
    dateOfBirth: String,
    onDateOfBirthSelected: (String) -> Unit
) {
    var isDatePickerShown by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // DatePickerDialog state
    val calendar = Calendar.getInstance()

    // DatePickerDialog listener
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            onDateOfBirthSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column {
        // Date of Birth TextField
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { /* Do nothing, handled by date picker */ },
            label = { Text("Date of Birth") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isDatePickerShown = true  // Show DatePicker when clicked
                },
            readOnly = true // Make the field read-only since we're using DatePicker
        )

        // Show the DatePicker dialog when `isDatePickerShown` is true
        if (isDatePickerShown) {
            LaunchedEffect(Unit) {
                datePickerDialog.show()
                isDatePickerShown = false  // Reset to prevent multiple popups
            }
        }
    }
}