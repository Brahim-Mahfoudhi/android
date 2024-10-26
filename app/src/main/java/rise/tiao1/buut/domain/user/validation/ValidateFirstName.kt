package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateFirstName @Inject constructor(){
    fun execute(firstName: String) : UiText? {
        if (firstName.isBlank())
            return  UiText.StringResource(resId = R.string.first_name_is_blank_error)

        return null
    }
}