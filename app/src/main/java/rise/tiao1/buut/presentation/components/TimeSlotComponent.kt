package rise.tiao1.buut.presentation.components
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.domain.booking.TimeSlot

@Composable
fun TimeSlotComponent(timeSlot: TimeSlot, onTimeSlotClicked: (TimeSlot) -> Unit) {
    Button(
        onClick = { onTimeSlotClicked(timeSlot) },
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp)
    ) {
        Text(timeSlot.slot)
    }
}