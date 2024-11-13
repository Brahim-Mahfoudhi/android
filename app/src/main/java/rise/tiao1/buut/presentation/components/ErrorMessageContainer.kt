package rise.tiao1.buut.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessageContainer(errorMessage: String) {
    Column(
        modifier = Modifier.widthIn(285.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = errorMessage,
            color = Color.Red
        )
    }
}