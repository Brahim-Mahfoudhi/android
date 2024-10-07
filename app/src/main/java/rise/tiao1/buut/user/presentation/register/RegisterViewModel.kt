package rise.tiao1.buut.user.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rise.tiao1.buut.user.domain.StreetType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


public class RegisterViewModel: ViewModel (){

    private val _uiState = mutableStateOf(RegisterScreenState())
    val uistate: State<RegisterScreenState> get() = _uiState


    private fun validateForm() {
        val firstNameError = if (_uiState.value.firstName.isBlank()) "First Name cannot be empty" else null
        val lastNameError = if (_uiState.value.lastName.isBlank()) "Last Name cannot be empty" else null
        val emailError = if (_uiState.value.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) "Invalid email address" else null
        val passwordError = if (!isPasswordValid(_uiState.value.password)) "Password must have at least 8 characters, one uppercase letter, one number, and one special character" else null
        val confirmPasswordError = if (_uiState.value.password != _uiState.value.confirmPassword) "Passwords do not match" else null
        val telephoneError = if (!isTelephoneValid(_uiState.value.telephone)) "Telephone number is invalid" else null
        val dateOfBirthError = if (!isUserAtLeast18(_uiState.value.dateOfBirth)) "You must be at least 18 years old" else null
        val streetError = if (_uiState.value.street == null) "You must select a street" else null
        val houseNumberError = if (_uiState.value.houseNumber.isBlank() || !_uiState.value.houseNumber.isDigitsOnly() || _uiState.value.houseNumber.toInt() <= 0) "House number must be greater than zero" else null
        val termsAgreementError = if (!_uiState.value.hasAgreedWithTermsOfUsage) "You must agree to the Terms of Usage to continue." else null
        val privacyAgreementError = if (!_uiState.value.hasAgreedWithPrivacyConditions) "You must agree to the Privacy Policy to continue." else null

        val isFormValid = firstNameError == null
                && lastNameError == null
                && emailError == null
                && passwordError == null
                && confirmPasswordError == null
                && telephoneError == null
                && dateOfBirthError == null
                && streetError == null
                && houseNumberError == null
                && termsAgreementError == null
                && privacyAgreementError == null

        _uiState.value = _uiState.value.copy(
            firstNameError = firstNameError,
            lastNameError = lastNameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            telephoneError = telephoneError,
            dateOfBirthError = dateOfBirthError,
            streetError = streetError,
            houseNumberError = houseNumberError,
            termsAgreementError = termsAgreementError,
            privacyAgreementError = privacyAgreementError,
            isFormValid = isFormValid
        )
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$"
        return password.matches(Regex(passwordRegex))
    }

    private fun isUserAtLeast18(dobString: String): Boolean {
        if (dobString.isBlank()) return false

        // Parse the selected date
        val dobFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
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

    fun isTelephoneValid(telephone: String): Boolean {
        //A Belgian telephone number is either 10 digits (GSM) or 9 (land-line)
        return (telephone.length == 10 || telephone.length == 9 ) && telephone.all { it.isDigit() }
    }

    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
        validateForm()
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
        validateForm()
    }

    fun onPasswordChanged(Password: String) {
        _uiState.value = _uiState.value.copy(password = Password)
        validateForm()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        validateForm()
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        validateForm()
    }

    fun onTelephoneChanged(telephone: String) {
        _uiState.value = _uiState.value.copy(telephone = telephone)
        validateForm()
    }

    fun onStreetSelected(street: StreetType) {
        _uiState.value = _uiState.value.copy(street = street)
        validateForm()
    }


    fun onHouseNumberChanged(houseNumber: String) {
        _uiState.value = _uiState.value.copy(houseNumber = houseNumber)
        validateForm()
    }

    fun onAddressAdditionChanged(addressAddition: String) {
        _uiState.value = _uiState.value.copy(addressAddition = addressAddition)
        validateForm()
    }

    fun onDateOfBirthSelected(dateOfBirth: String) {
        _uiState.value = _uiState.value.copy(dateOfBirth = dateOfBirth)
        validateForm()
    }

    fun onAcceptTermsChanged(isChecked: Boolean) {
        _uiState.value = _uiState.value.copy(hasAgreedWithTermsOfUsage = isChecked)
        validateForm()
    }

    fun onAcceptPrivacyChanged(isChecked: Boolean) {
        _uiState.value = _uiState.value.copy(hasAgreedWithPrivacyConditions = isChecked)
        validateForm()
    }



    fun onRegisterClick() {
        viewModelScope.launch {
            if (_uiState.value.isFormValid) {
                //todo: call naar BE.
            }
        }
    }
}

