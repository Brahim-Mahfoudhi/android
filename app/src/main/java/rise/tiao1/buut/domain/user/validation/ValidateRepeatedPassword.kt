package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateRepeatedPassword @Inject constructor(){
    fun execute(password: String, repeatedPassword: String) : UiText? {

        if (password != repeatedPassword)
            return  UiText.StringResource(resId = R.string.repeated_password_error)

        return null
    }
}