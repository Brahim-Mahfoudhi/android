package rise.tiao1.buut.presentation.register
import rise.tiao1.buut.utils.StreetType
import rise.tiao1.buut.utils.UiText


/**
 * Data class representing the state of the registration screen.
 *
 * This class holds information about the current form state, validations status,
 * and any potential error that might have occurred during registration.
 *
 * @property firstName The [String] object representing the first name of the User.
 * @property lastName The [String] object representing the last name of the User.
 * @property telephone The [String] object representing the telephone of the User.
 * @property street The [StreetType] object representing the street of the User.
 * @property houseNumber The [String] object representing the house number of the User.
 * @property addressAddition The [String] object representing addition to the address of the User.
 * @property email The [String] object representing the email of the User.
 * @property password The [String] object representing the password of the User.
 * @property confirmPassword The [String] object representing the confirmed password of the User.
 * @property dateOfBirth The [String] object representing the date of birth of the User.
 * @property isFormValid The [Boolean] object representing whether the form is fully valid.
 * @property hasAgreedWithTermsOfUsage The [Boolean] object representing whether the user has agreed with the Terms of Usage.
 * @property hasAgreedWithPrivacyConditions The [Boolean] object representing whether the user has agreed with the Privacy Policy.
 * @property firstNameError The [String] object representing the errors in the validator of [firstName].
 * @property lastNameError The [String] object representing the errors in the validator of [lastName].
 * @property telephoneError The [String] object representing the errors in the validator of [telephone].
 * @property emailError The [String] object representing the errors in the validator of [email].
 * @property passwordError The [String] object representing the errors in the validator of [password].
 * @property confirmPassword The [String] object representing the errors in the password confirmation.
 * @property dateOfBirthError The [String] object representing the errors in the validator of [dateOfBirth].
 * @property streetError The [String] object representing the errors in the validator of [StreetType].
 * @property houseNumberError The [String] object representing the errors in the validator of [Number].
 * @property dateOfBirthError The [String] object representing the errors in the validator of [dateOfBirth].
 * @property termsAgreementError The [String] object representing the errors in the agreement to the Terms of Usage.
 * @property privacyAgreementError The [String] object representing the errors in the agreement to the Privacy Policy.
 * @constructor Creates an instance of [RegisterScreenState] with the provided parameters.
 */

data class RegistrationScreenState(
    val firstName: String = "",
    val firstNameError: UiText? = null,
    val lastName: String = "",
    val lastNameError: UiText? = null,
    val street: StreetType? = null,
    val streetError: UiText? = null,
    val houseNumber: String = "",
    val houseNumberError: UiText? = null,
    val box: String = "",
    val phone: String = "",
    val phoneError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: UiText? = null,
    val dateOfBirth: String = "",
    val dateOfBirthError: UiText? = null,
    val acceptedTermsOfUsage: Boolean = false,
    val termsError: UiText? = null,
    val acceptedPrivacyConditions: Boolean = false,
    val privacyError: UiText? = null,
)
