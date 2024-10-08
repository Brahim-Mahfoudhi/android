package rise.tiao1.buut.user.presentation.register

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rise.tiao1.buut.user.domain.StreetType
import rise.tiao1.buut.user.domain.dto.RegistrationDTO
import rise.tiao1.buut.user.domain.dto.UserOutPutDTO
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class RegisterViewModel : ViewModel() {

    //Tijdelijke variables opdat we de REST API response kunnen testen zonder volledige applicatie
    private val _registrationResult = mutableStateOf<UserOutPutDTO?>(null)
    val registrationResult: State<UserOutPutDTO?> get() = _registrationResult

    //State variables
    private val _uiState = mutableStateOf(RegisterScreenState())
    val uistate: State<RegisterScreenState> get() = _uiState

    //state variables for date validation from picker
    var selectedDay by mutableStateOf("")
    var selectedMonth by mutableStateOf("")
    var selectedYear by mutableStateOf("")

    //field interaction state variables
    var firstNameTouched by mutableStateOf(false)
    var lastNameTouched by mutableStateOf(false)
    var emailTouched by mutableStateOf(false)
    var phoneNumberTouched by mutableStateOf(false)
    var streetTouched by mutableStateOf(false)
    var houseNumberTouched by mutableStateOf(false)
    var passwordTouched by mutableStateOf(false)
    var passwordConfirmTouched by mutableStateOf(false)
    var dateOfBirthTouched by mutableStateOf(false)
    var termsTouched by mutableStateOf(false)
    var privacyTouched by mutableStateOf(false)

    // Validation rules for each field
    fun validateFirstName() {
        if (!firstNameTouched) return
        val firstNameError = if (_uiState.value.firstName.isBlank()) "First Name cannot be empty" else null
        _uiState.value = _uiState.value.copy(firstNameError = firstNameError)
    }

    fun validateLastName() {
        if (!lastNameTouched) return
        val lastNameError = if (_uiState.value.lastName.isBlank()) "Last Name cannot be empty" else null
        _uiState.value = _uiState.value.copy(lastNameError = lastNameError)
    }

    fun validateEmail(){
        if (!emailTouched) return
        val emailError =
            if (_uiState.value.email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email)
                    .matches()
            ) "Invalid email address" else null
        _uiState.value = _uiState.value.copy(emailError = emailError)
    }

    fun validatePassword(){
        if (!passwordTouched) return
        val passwordError = if (!isPasswordValid(_uiState.value.password)) "Password must have at least 8 characters, one uppercase letter, one number, and one special character" else null
        _uiState.value = _uiState.value.copy(passwordError = passwordError)
    }

    fun validateConfirmPassword(){
        if (!passwordConfirmTouched) return
        val confirmPasswordError = if (_uiState.value.password != _uiState.value.confirmPassword) "Passwords do not match" else null
        _uiState.value = _uiState.value.copy(confirmPasswordError = confirmPasswordError)
    }

    fun validateTelephone(){
        if (!phoneNumberTouched) return
        val telephoneError = if (!isTelephoneValid(_uiState.value.telephone)) "Telephone number is invalid" else null
        _uiState.value = _uiState.value.copy(telephoneError = telephoneError)
    }

    fun validateDateOfBirthDropDown(){
        if (!dateOfBirthTouched) return
        val dateOfBirthError = validateDateOfBirth() //returns error string if date invalid or use < 18 years old. Else null.
        _uiState.value = _uiState.value.copy(dateOfBirth = dateOfBirthError.toString())
    }

    fun validateStreet(){
        if (!streetTouched) return
        val streetError = if (_uiState.value.street == null) "You must select a street" else null
        _uiState.value = _uiState.value.copy(streetError = streetError)
    }

    fun validateHouseNumber(){
        if (!houseNumberTouched) return
        val houseNumberError = if (_uiState.value.houseNumber.isBlank() || !_uiState.value.houseNumber.isDigitsOnly() || _uiState.value.houseNumber.toInt() <= 0) "House number must be greater than zero" else null
        _uiState.value = _uiState.value.copy(houseNumberError = houseNumberError)
    }

    fun validateTerms(){
        if (!termsTouched) return
        val termsAgreementError = if (!_uiState.value.hasAgreedWithTermsOfUsage) "You must agree to the Terms of Usage to continue." else null
        _uiState.value = _uiState.value.copy(termsAgreementError = termsAgreementError)
    }

    fun validatePrivacy(){
        if (!privacyTouched) return
        val privacyAgreementError = if (!_uiState.value.hasAgreedWithPrivacyConditions) "You must agree to the Privacy Policy to continue." else null
        _uiState.value = _uiState.value.copy(privacyAgreementError = privacyAgreementError)
    }

    //checks if all fields have been touched and errors exist based on previous validators and sets overal form valid to true for POST
    private fun validateForm() {
        val AllFieldsTouched = firstNameTouched
                && lastNameTouched
                && emailTouched
                && phoneNumberTouched
                && streetTouched
                && houseNumberTouched
                && passwordTouched
                && passwordConfirmTouched
                && dateOfBirthTouched
                && termsTouched
                && privacyTouched

        val NoErrors = _uiState.value.firstNameError == null
                && _uiState.value.lastNameError == null
                && _uiState.value.emailError == null
                && _uiState.value.passwordError == null
                && _uiState.value.confirmPasswordError == null
                && _uiState.value.telephoneError == null
                && _uiState.value.dateOfBirthError == null
                && _uiState.value.streetError == null
                && _uiState.value.houseNumberError == null
                && _uiState.value.termsAgreementError == null
                && _uiState.value.privacyAgreementError == null

        _uiState.value = _uiState.value.copy(isFormValid = AllFieldsTouched && NoErrors)
    }


    //Helper functions for validation
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

    //handlers for fields in form
    fun onFirstNameChanged(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
        validateFirstName()
        validateForm()
    }

    fun onLastNameChanged(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
        validateLastName()
        validateForm()
    }

    fun onPasswordChanged(Password: String) {
        _uiState.value = _uiState.value.copy(password = Password)
        validatePassword()
        validateForm()
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        validateConfirmPassword()
        validateForm()
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        validateEmail()
        validateForm()
    }

    fun onTelephoneChanged(telephone: String) {
        _uiState.value = _uiState.value.copy(telephone = telephone)
        validateTelephone()
        validateForm()
    }

    fun onStreetSelected(street: StreetType) {
        _uiState.value = _uiState.value.copy(street = street)
        validateStreet()
        validateForm()
    }

    fun onHouseNumberChanged(houseNumber: String) {
        _uiState.value = _uiState.value.copy(houseNumber = houseNumber)
        validateHouseNumber()
        validateForm()
    }

    fun onAddressAdditionChanged(addressAddition: String) {
        _uiState.value = _uiState.value.copy(addressAddition = addressAddition)
        //does not trigger validation since addition is a free form.
    }

    fun OnDaySelected(day: String) {
        selectedDay = day
        validateDateOfBirthDropDown()
        validateForm()
    }

    fun onMonthSelected(month: String) {
        selectedMonth = month
        validateDateOfBirthDropDown()
        validateForm()
    }

    fun onYearSelected(year: String) {
        selectedYear = year
        validateDateOfBirthDropDown()
        validateForm()
    }

    fun onAcceptTermsChanged(isChecked: Boolean) {
        _uiState.value = _uiState.value.copy(hasAgreedWithTermsOfUsage = isChecked)
        validateTerms()
        validateForm()
    }

    fun onAcceptPrivacyChanged(isChecked: Boolean) {
        _uiState.value = _uiState.value.copy(hasAgreedWithPrivacyConditions = isChecked)
        validatePrivacy()
        validateForm()
    }

    fun onRegisterClick() {
        if (_uiState.value.isFormValid) {
            //todo: call naar BE.
            viewModelScope.launch {
                val registerDto = RegistrationDTO(
                    firstName = _uiState.value.firstName,
                    lastName = _uiState.value.lastName,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    telephone = _uiState.value.telephone,
                    dateOfBirth = _uiState.value.dateOfBirth,
                    street = _uiState.value.street,
                    houseNumber = _uiState.value.houseNumber,
                    addressAddition = _uiState.value.addressAddition,
                )
                //Ik krijg dit niet geinjecteerd
//                     val user = userRepository.registerUser(registerDto)
//                     _registrationResult.value = user
            }
        }
    }
}

