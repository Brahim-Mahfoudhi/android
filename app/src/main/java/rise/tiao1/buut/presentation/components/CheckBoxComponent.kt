package rise.tiao1.buut.presentation.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
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
    @StringRes label: Int,
    @StringRes url: Int? = null
) {
    Row(modifier = Modifier
        .fillMaxWidth(),
        //.heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically) {
        val context = LocalContext.current

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
            ClickableTextComponent(leadingText, label, onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url?.let { context.getString(url) }))
                    context.startActivity(intent)

            } )

            if (!errorMessage.isNullOrEmpty())
                ErrorMessageContainer(errorMessage)
        }

    }
}