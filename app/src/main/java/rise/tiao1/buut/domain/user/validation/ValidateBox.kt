package rise.tiao1.buut.domain.user.validation

import androidx.core.text.isDigitsOnly
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject



class ValidateBox @Inject constructor(){
    fun execute(box: String) : UiText? {
        if (box.isBlank() || box.isDigitsOnly())
            return UiText.StringResource(resId = R.string.invalid_box)

        return null
    }
}