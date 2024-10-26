package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

const val PASSWORD_LENGTH = 8

class ValidatePassword @Inject constructor(){
    fun execute(password: String) : UiText? {

        if (password.length < PASSWORD_LENGTH)
            return  UiText.StringResource(resId = R.string.invalid_password_length_error, PASSWORD_LENGTH)

        if (!password.any {it.isDigit()})
            return  UiText.StringResource(resId = R.string.invalid_password_no_digit_error)

        if (!password.any {it.isUpperCase()})
            return  UiText.StringResource(resId = R.string.invalid_password_no_upper_case_error)

        return null
    }
}