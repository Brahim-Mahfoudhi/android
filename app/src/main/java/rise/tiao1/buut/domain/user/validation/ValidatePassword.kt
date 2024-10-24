package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

//const val PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$"
const val PASSWORD_LENGTH = 8

class ValidatePassword @Inject constructor(){
    fun execute(password: String) : ValidationResult {

        if (password.length < PASSWORD_LENGTH) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalid_password_length_error, PASSWORD_LENGTH)
            )
        }

        if (!password.any {it.isDigit()}) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalid_password_no_digit_error)
            )
        }

        if (!password.any {it.isUpperCase()}) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalid_password_no_upper_case_error)
            )
        }

        return ValidationResult(succesful = true)
    }
}