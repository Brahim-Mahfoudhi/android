package rise.tiao1.buut.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.lang.Error

@Composable
fun CheckboxComponent(
    value : Boolean,
    onChecked: (Boolean) -> Unit,
    errorMessage: String? = null,
    @StringRes leadingText: Int,
    @StringRes label: Int
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Checkbox(modifier = Modifier.testTag(stringResource(label)),
            checked = value,
            onCheckedChange = {
            onChecked(!value)
        },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.White,
                uncheckedColor = Color.White,
                checkmarkColor = Color.Black
            )
        )

        Column {
            Text(
                text = "${stringResource(leadingText)}  ${stringResource(label)}",
                color = Color.White
            )

            if (!errorMessage.isNullOrEmpty())
                ErrorMessageContainer(errorMessage)
        }

    }
}