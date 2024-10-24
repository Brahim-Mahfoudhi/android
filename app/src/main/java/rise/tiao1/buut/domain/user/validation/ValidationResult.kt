package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.utils.UiText

data class ValidationResult(
    val succesful: Boolean,
    val errorMessage: UiText? = null
)
