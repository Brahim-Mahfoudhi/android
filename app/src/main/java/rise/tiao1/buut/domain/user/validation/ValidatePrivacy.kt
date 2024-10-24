package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidatePrivacy @Inject constructor(){
    fun execute(acceptedPrivacy: Boolean) : ValidationResult {
        if (!acceptedPrivacy) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.privacy_not_accepted_error)
            )
        }

        return ValidationResult(succesful = true)
    }
}