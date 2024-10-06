package rise.tiao1.buut.user.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@HiltViewModel
class RegisterViewModel: ViewModel (){

    private val _uiState = mutableStateOf(RegisterScreenState())
    val uistate: State<RegisterScreenState> get() = _uiState


    private fun validateForm() {
        val firstNameError = if (_uiState.value.firstName.isBlank()) "First Name cannot be empty" else null
        val lastNameError = if (_uiState.value.lastName.isBlank()) "Last Name cannot be empty" else null
        val emailError = if (_uiState.value.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) "Invalid email address" else null
        val passwordError = if (!isPasswordValid(_uiState.value.password)) "Password must have at least 8 characters, one uppercase letter, one number, and one special character" else null
        val confirmPasswordError = if (_uiState.value.password != _uiState.value.confirmPassword) "Passwords do not match" else null
        val telephoneError = if (_uiState.value.telephone.isBlank()) "Telephone number cannot be empty" else null
        val dateOfBirthError = if (!isUserAtLeast18(_uiState.value.dateOfBirth)) "You must be at least 18 years old" else null
        val numberError = if (_uiState.value.number.toInt() <= 0 ) "House Number must be higher than 0" else null
        val agreementError = if (_uiState.value.hasAgreedWithTermsOfUsage != true || _uiState.value.hasAgreedWithPrivacyConditions != true) "You must agree to the Terms of Usage and Privacy Policy to continue." else null

        val isFormValid = firstNameError == null
                && lastNameError == null
                && emailError == null
                && passwordError == null
                && confirmPasswordError == null
                && telephoneError == null
                && dateOfBirthError == null
                && numberError == null
                && agreementError == null

        _uiState.value = _uiState.value.copy(
            firstNameError = firstNameError,
            lastNameError = lastNameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            telephoneError = telephoneError,
            dateOfBirthError = dateOfBirthError,
            numberError = numberError,
            agreementError = agreementError,
            isFormValid = isFormValid
        )
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$"
        return password.matches(Regex(passwordRegex))
    }

    fun isUserAtLeast18(dobString: String): Boolean {
        if (dobString.isBlank()) return false

        // Parse the selected date
        val dobFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        val dob = dobFormat.parse(dobString)

        // Get current date and calculate the age
        val calendar = Calendar.getInstance()
        val today = calendar.time

        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dob

        val age = calendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
        if (calendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            return age - 1 >= 18
        }

        return age >= 18
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            if (_uiState.value.isFormValid) {
                //todo: call naar BE.
            }
        }
    }
}

