package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateLastName @Inject constructor(){
    fun execute(lastName: String) : ValidationResult {
        if (lastName.isBlank()) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.last_name_is_blank_error)
            )
        }

        return ValidationResult(succesful = true)
    }
}