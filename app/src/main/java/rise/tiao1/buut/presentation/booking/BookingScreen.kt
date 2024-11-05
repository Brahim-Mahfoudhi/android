package rise.tiao1.buut.presentation.booking

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun BookingScreen(
    state: BookingScreenState,
    onValueChanged: (input: Long?) -> Unit,
) {
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row {
            BookingLandscapeLayout(state = state, onValueChanged = onValueChanged)
        }
    } else {
        BookingPortraitLayout(state = state, onValueChanged = onValueChanged)
    }
}


