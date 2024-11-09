package rise.tiao1.buut.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            strokeWidth = 3.dp,
            modifier = Modifier
                .size(60.dp)
                .semantics { this.testTag = "LoadingIndicator" }
        )
    }
}