package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateStreet @Inject constructor(){
    fun execute(street: String) : ValidationResult {
        if (street.isNullOrEmpty()) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.street_is_blank_error)
            )
        }

        return ValidationResult(succesful = true)
    }
}