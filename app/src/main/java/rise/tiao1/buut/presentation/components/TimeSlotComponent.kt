package rise.tiao1.buut.presentation.components
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.TimeSlot

@Composable
fun TimeSlotComponent(timeSlot: TimeSlot, onTimeSlotClicked: (TimeSlot) -> Unit) {
    Button(
        onClick = { onTimeSlotClicked(timeSlot) },
        enabled = timeSlot.available,
        modifier = Modifier.widthIn(dimensionResource(R.dimen.button_width))

    ) {
        Text(timeSlot.slot)
    }
}