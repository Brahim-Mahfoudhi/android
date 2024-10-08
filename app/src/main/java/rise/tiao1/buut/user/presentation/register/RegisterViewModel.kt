package rise.tiao1.buut.user.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rise.tiao1.buut.user.domain.StreetType
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


public class RegisterViewModel: ViewModel (){

    private val _uiState = mutableStateOf(RegisterScreenState())
    val uistate: State<RegisterScreenState> get() = _uiState
    var selectedDay by mutableStateOf("")
    var selectedMonth by mutableStateOf("")
    var selectedYear by mutableStateOf("")


    private fun validateForm() {
        val firstNameError = if (_uiState.value.firstName.isBlank()) "First Name cannot be empty" else null
        val lastNameError = if (_uiState.value.lastName.isBlank()) "Last Name cannot be empty" else null
        val emailError = if (_uiState.value.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()) "Invalid email address" else null
        val passwordError = if (!isPasswordValid(_uiState.value.password)) "Password must have at least 8 characters, one uppercase letter, one number, and one special character" else null
        val confirmPasswordError = if (_uiState.value.password != _uiState.value.confirmPassword) "Passwords do not match" else null
        val telephoneError = if (!isTelephoneValid(_uiState.value.telephone)) "Telephone number is invalid" else null
        val dateOfBirthError = validateDateOfBirth() //returns error string if date invalid or use < 18 years old. Else null.
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
            dateOfBirthError = dateOfBirthError.toString(),
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

    // Validates if the user is at least 18 and if the date is possible
    fun validateDateOfBirth(): Any? {

        //If date is empty, error message
        if (selectedDay.isEmpty() || selectedMonth.isEmpty() || selectedYear.isEmpty()) {
            return "Please select day, month, and year"
        }

        //parse dropdown string to int for parsing in later function
        val monthNumber = when (selectedMonth) {
            "January" -> 1
            "February" -> 2
            "March" -> 3
            "April" -> 4
            "May" -> 5
            "June" -> 6
            "July" -> 7
            "August" -> 8
            "September" -> 9
            "October" -> 10
            "November" -> 11
            "December" -> 12
            else -> null
        }

        //Shouldn't be possible, but in case the when clause goes into the else from weird UI injection.
        if (monthNumber == null) {
            return "Invalid month"
        }

        //Shouldn't be possible, but in case weird UI injection happens.
        if (selectedDay.toInt() <= 0 || selectedDay.toInt() > 31 ) {
            return "Invalid day"
        }

        //Shouldn't be possible, but in case weird UI injection happens.
        if (selectedYear.toInt() <= LocalDate.now().minusYears(100L).year || selectedYear.toInt() > LocalDate.now().year ) {
            return "Invalid year"
        }

        try {
            //Turn year month and day field into string for parsing. yyyy-MM-dd used for convenience in documentation
            val dateStr = "$selectedYear-${monthNumber.toString().padStart(2, '0')}-${selectedDay.padStart(2, '0')}"
            //Try to parse the date. If date is impossible (like 31st of february) it will throw exception
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            //if this line is reached, the date must be valid and can be used to check age of the user.
            if (isUserAtLeast18()) {
                //null is returned to denote the lack of error. Empty string would not work.
                return null
            } else {
                return "You must be at least 18 years old"
            }
            //catch exception in case of impossible date.
        } catch (e: DateTimeParseException) {
            return "Invalid date"
        }
    }

    private fun isUserAtLeast18(): Boolean {
        if (selectedDay.isEmpty() || selectedMonth.isEmpty() || selectedYear.isEmpty()) {
            return false
        }
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val birthDate = LocalDate.parse("$selectedDay/$selectedMonth/$selectedYear", formatter)
        val currentDate = LocalDate.now()
        val age = Period.between(birthDate, currentDate).years
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

