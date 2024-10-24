package rise.tiao1.buut.presentation.login

import rise.tiao1.buut.utils.FieldKeys
import rise.tiao1.buut.utils.FieldKeys.*

data class LoginScreenState (
    val fields: Map<FieldKeys, String> = mapOf(
       EMAIL to "",
        PASSWORD to ""
    ),
    val errors: Map<FieldKeys, String> = mapOf(
       EMAIL to "",
       PASSWORD to ""
    ),
)