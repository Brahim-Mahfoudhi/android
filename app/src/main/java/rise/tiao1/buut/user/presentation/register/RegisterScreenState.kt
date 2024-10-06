package rise.tiao1.buut.user.presentation.register


import rise.tiao1.buut.user.domain.StreetType


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
 * @property number The [String] object representing the house number of the User.
 * @property addition The [String] object representing addition to the address of the User.
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
 * @property agreementError The [String] object representing the errors in the agreement to the Terms of Usage or Privacy Policy.
 * @constructor Creates an instance of [RegisterScreenState] with the provided parameters.
 */

data class RegisterScreenState(
    val firstName: String = "",
    val lastName: String = "",
    val street: StreetType = StreetType.AFRIKALAAN,
    val number: String = "",
    val addition: String = "",
    val telephone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val dateOfBirth: String = "",
    val isFormValid: Boolean = false,
    val hasAgreedWithTermsOfUsage: Boolean = false,
    val hasAgreedWithPrivacyConditions: Boolean = false,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val telephoneError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val dateOfBirthError: String? = null,
    val numberError: String? = null,
    val agreementError: String? = null
)
