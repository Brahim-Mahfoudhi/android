package rise.tiao1.buut.domain.user.validation

import rise.tiao1.buut.R
import rise.tiao1.buut.utils.UiText
import javax.inject.Inject

class ValidateTerms @Inject constructor(){
    fun execute(acceptedTerms: Boolean) : UiText? {

        if (!acceptedTerms)
            return  UiText.StringResource(resId = R.string.terms_not_accepted_error)

        return null
    }
}