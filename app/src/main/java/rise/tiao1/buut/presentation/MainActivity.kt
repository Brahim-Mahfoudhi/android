package rise.tiao1.buut.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import rise.tiao1.buut.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import rise.tiao1.buut.navigation.Navigation


/**
 * Main activity class for the Buut application.
 */
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isAppWakeUp = false

    /**
     * Called when the activity is resumed. Sets the `isAppWakeUp` flag to true.
     */
    override fun onResume() {
        super.onResume()
        isAppWakeUp = true
    }

    /**
     * Called when the activity is started. Sets the `isAppWakeUp` flag to true.
     */
    override fun onStart() {
        super.onStart()
        isAppWakeUp = true
    }

    /**
     * Called when the activity is created. Sets the content of the activity using Jetpack Compose.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                BuutApp(this, isAppWakeUp)
            }
        }
    }

    /**
     * Composable function representing the IrrigatorApp UI.
     *
     * @param mainActivity Reference to the [MainActivity].
     * @param isAppWakeUp Boolean flag indicating whether the app is waking up.
     */
    @Composable
    fun BuutApp(mainActivity: MainActivity, isAppWakeUp: Boolean) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Navigation(mainActivity, isAppWakeUp)
            }
        }
    }
}
