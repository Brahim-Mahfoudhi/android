package rise.tiao1.buut.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import rise.tiao1.buut.R

@Composable
fun NotificationModal(
    notification: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        modifier = Modifier.testTag("notificationModal"),
        onDismissRequest = onDismiss,
        text = {
            Text(text = notification, modifier = Modifier.testTag("notificationText"))
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(R.string.close_label))
            }
        },
    )
}