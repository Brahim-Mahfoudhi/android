package rise.tiao1.buut.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.TimeSlot

@Composable
fun BookingConfirmationModal(timeSlot: TimeSlot?, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.confirm_booking_modal_header)) },
        text = { Text(stringResource(R.string.confirm_booking_prompt) + " ${timeSlot?.slot}?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}