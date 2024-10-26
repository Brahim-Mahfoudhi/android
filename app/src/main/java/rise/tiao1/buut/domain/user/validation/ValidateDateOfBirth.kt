package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import rise.tiao1.buut.utils.toDate
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

const val MINIMUM_AGE = 18

class ValidateDateOfBirth @Inject constructor(){
    fun execute(dateOfBirth: String) : UiText? {

        if (dateOfBirth.isNotEmpty()) {
            val currentDate = LocalDate.now()
            val age = Period.between(dateOfBirth.toDate(), currentDate).years

            if (age >= MINIMUM_AGE)
                return null

        }
        return UiText.StringResource(resId = R.string.minimum_age_error, MINIMUM_AGE)

    }

}