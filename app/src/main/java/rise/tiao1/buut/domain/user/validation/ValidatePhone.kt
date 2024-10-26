package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

const val PHONE_REGEX = "\\d{9,10}$"

class ValidatePhone @Inject constructor(){
    fun execute(phone: String) : UiText? {

        if (!phone.matches(Regex(PHONE_REGEX)))
            return  UiText.StringResource(resId = R.string.invalid_phone_error)

        return null
    }
}