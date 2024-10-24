package rise.tiao1.buut.domain.user.validation

import androidx.core.text.isDigitsOnly
import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

const val LOWEST_POSSIBLE_HOUSE_NUMBER = 1

class ValidateHouseNumber @Inject constructor(){
    fun execute(houseNumber: String) : ValidationResult {
        if (houseNumber.isBlank() || !houseNumber.isDigitsOnly() || houseNumber.toInt() < LOWEST_POSSIBLE_HOUSE_NUMBER) {
            return ValidationResult(
                succesful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalid_house_number_error, LOWEST_POSSIBLE_HOUSE_NUMBER)
            )
        }

        return ValidationResult(succesful = true)
    }
}