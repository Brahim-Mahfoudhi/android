package rise.tiao1.buut.domain.user.validation

import android.util.Patterns
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateEmail @Inject constructor(){
    fun execute(email: String) : UiText? {
        if (email.isBlank())
                return UiText.StringResource(resId = R.string.email_is_blank_error)

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return  UiText.StringResource(resId = R.string.email_not_valid_error)

        return null
    }
}