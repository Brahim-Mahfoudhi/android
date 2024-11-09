package rise.tiao1.buut.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import rise.tiao1.buut.R

@Composable
fun InfoContainer(infoMessage: String) {
    Surface(
    color = MaterialTheme.colorScheme.tertiaryContainer,
    shape = MaterialTheme.shapes.large,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_large),
                    vertical = dimensionResource(R.dimen.padding_large)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Lightbulb,
                contentDescription = stringResource(R.string.lightbulb),
                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = infoMessage,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}