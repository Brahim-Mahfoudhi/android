package rise.tiao1.buut.presentation.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rise.tiao1.buut.data.remote.user.dto.AddressOutPutDTO
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.data.remote.user.dto.RegistrationDTO
import rise.tiao1.buut.domain.user.useCases.RegisterUserUseCase
import rise.tiao1.buut.domain.user.validation.ValidateDateOfBirth
import rise.tiao1.buut.domain.user.validation.ValidateEmail
import rise.tiao1.buut.domain.user.validation.ValidateFirstName
import rise.tiao1.buut.domain.user.validation.ValidateHouseNumber
import rise.tiao1.buut.domain.user.validation.ValidateLastName
import rise.tiao1.buut.domain.user.validation.ValidatePassword
import rise.tiao1.buut.domain.user.validation.ValidatePhone
import rise.tiao1.buut.domain.user.validation.ValidatePrivacy
import rise.tiao1.buut.domain.user.validation.ValidateRepeatedPassword
import rise.tiao1.buut.domain.user.validation.ValidateStreet
import rise.tiao1.buut.domain.user.validation.ValidateTerms
import rise.tiao1.buut.utils.RegistrationFieldKey
import rise.tiao1.buut.utils.toApiDateString
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateFirstName: ValidateFirstName,
    private val validateLastName: ValidateLastName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateRepeatedPassword: ValidateRepeatedPassword,
    private val validateStreet: ValidateStreet,
    private val validateHouseNumber: ValidateHouseNumber,
    private val validateDateOfBirth: ValidateDateOfBirth,
    private val validatePhone: ValidatePhone,
    private val validateTerms: ValidateTerms,
    private val validatePrivacy: ValidatePrivacy,
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {

    private val _state = mutableStateOf(RegistrationScreenState())
    val state: State<RegistrationScreenState>
        get() = _state

    fun update(inputValue: String, field: RegistrationFieldKey) {
        when(field) {
            RegistrationFieldKey.FIRST_NAME -> {_state.value = _state.value.copy(firstName = inputValue)}
            RegistrationFieldKey.LAST_NAME -> {_state.value = _state.value.copy(lastName = inputValue)}
            RegistrationFieldKey.EMAIL -> {_state.value = _state.value.copy(email = inputValue)}
            RegistrationFieldKey.PHONE -> {_state.value = _state.value.copy(phone = inputValue)}
            RegistrationFieldKey.STREET -> {_state.value = _state.value.copy(street = StreetType.fromString(inputValue))}
            RegistrationFieldKey.HOUSE_NUMBER -> {_state.value = _state.value.copy(houseNumber = inputValue)}
            RegistrationFieldKey.BOX -> {_state.value = _state.value.copy(box = inputValue)}
            RegistrationFieldKey.DATE_OF_BIRTH -> {_state.value = _state.value.copy(dateOfBirth = inputValue)}
            RegistrationFieldKey.PASSWORD -> {_state.value = _state.value.copy(password = inputValue)}
            RegistrationFieldKey.REPEATED_PASSWORD -> {_state.value = _state.value.copy(repeatedPassword = inputValue)}
            else -> {}
        }
    }

    fun updateAcceptedAgreements(inputValue: Boolean, field: RegistrationFieldKey) {
        when (field) {
            RegistrationFieldKey.TERMS -> {_state.value = _state.value.copy(acceptedTermsOfUsage = inputValue)}
            RegistrationFieldKey.PRIVACY -> {_state.value = _state.value.copy(acceptedPrivacyConditions = inputValue)}
            else -> {}
        }
    }

    fun validateField(field: RegistrationFieldKey) {
        when(field) {
            RegistrationFieldKey.FIRST_NAME -> {_state.value = _state.value.copy(firstNameError = validateFirstName.execute(_state.value.firstName).errorMessage)}
            RegistrationFieldKey.LAST_NAME -> {_state.value = _state.value.copy(lastNameError = validateLastName.execute(_state.value.lastName).errorMessage)}
            RegistrationFieldKey.EMAIL -> {_state.value = _state.value.copy(emailError = validateEmail.execute(_state.value.email).errorMessage)}
            RegistrationFieldKey.PHONE -> {_state.value = _state.value.copy(phoneError = validatePhone.execute(_state.value.phone).errorMessage)}
            RegistrationFieldKey.STREET -> {_state.value = _state.value.copy(streetError = validateStreet.execute(_state.value.street.toString()).errorMessage)}
            RegistrationFieldKey.HOUSE_NUMBER -> {_state.value = _state.value.copy(houseNumberError = validateHouseNumber.execute(_state.value.houseNumber).errorMessage)}
            //Registration.BOX -> {_state.value = _state.value.copy(boxError = validateFirstName.execute(_state.value.firstName).errorMessage)}
            RegistrationFieldKey.DATE_OF_BIRTH -> {_state.value = _state.value.copy(dateOfBirthError = validateDateOfBirth.execute(_state.value.dateOfBirth).errorMessage)}
            RegistrationFieldKey.PASSWORD -> {_state.value = _state.value.copy(passwordError = validatePassword.execute(_state.value.password).errorMessage)}
            RegistrationFieldKey.REPEATED_PASSWORD -> {_state.value = _state.value.copy(repeatedPasswordError = validateRepeatedPassword.execute(_state.value.password, _state.value.repeatedPassword).errorMessage)}
            RegistrationFieldKey.TERMS -> {_state.value = _state.value.copy(termsError = validateTerms.execute(_state.value.acceptedTermsOfUsage).errorMessage)}
            RegistrationFieldKey.PRIVACY -> {_state.value = _state.value.copy(privacyError = validatePrivacy.execute(_state.value.acceptedPrivacyConditions).errorMessage)}
            else -> {}
        }
    }


    fun onRegisterClick() {

        RegistrationFieldKey.entries.forEach {
            validateField(it)
        }

        val formHasErrors = listOf(
            _state.value.firstNameError,
            _state.value.lastNameError,
            _state.value.emailError,
            _state.value.phoneError,
            _state.value.streetError,
            _state.value.houseNumberError,
            _state.value.dateOfBirthError,
            _state.value.passwordError,
            _state.value.repeatedPasswordError,
            _state.value.termsError,
            _state.value.privacyError
        ).any { it != null }

        if(!formHasErrors) {

            val addressDto = AddressOutPutDTO(
                street = _state.value.street,
                houseNumber = _state.value.houseNumber,
                bus = _state.value.box
            )
            val newUser = RegistrationDTO(
            firstName = _state.value.firstName,
            lastName = _state.value.lastName,
            email = _state.value.email,
            password = _state.value.password,
            phoneNumber = _state.value.phone,
            birthDate = _state.value.dateOfBirth.toApiDateString(),
            address = addressDto
            )

            viewModelScope.launch {
                try {
                    val response = registerUserUseCase(newUser)
                    Log.d("RegistrationViewModel", "Registratie succesvol: $response")
                } catch (e: Exception) {
                    Log.e("API_ERROR", e.message.toString())
                }

            }
        }

    }
}

