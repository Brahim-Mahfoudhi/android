package rise.tiao1.buut.presentation.booking.createBooking

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.InfoContainer
import rise.tiao1.buut.presentation.components.LoadingIndicator
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.UiLayout
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_EXPANDED
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_MEDIUM
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_SMALL
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_EXPANDED
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_MEDIUM
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_SMALL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookingScreen(
    state: CreateBookingScreenState,
    onReadyForUpdate: () -> Unit,
    onMonthChanged: (input: Long) -> Unit = {},
    navigateUp: () -> Unit = {},
    uiLayout: UiLayout
) {
    Scaffold(
        topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Image(
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.image_size_standard))
                                .padding(top = dimensionResource(R.dimen.padding_small)),
                            painter = painterResource(R.drawable.buut_logo),
                            contentDescription = stringResource(R.string.buut_logo),
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button)
                            )
                        }
                    }
                )

        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiLayout) {
                PORTRAIT_SMALL, PORTRAIT_MEDIUM, PORTRAIT_EXPANDED -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.7f)
                                .fillMaxSize()
                        ) {
                            DatePicker(state, onReadyForUpdate, onMonthChanged)
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxSize()
                        ) {
                            TimeSlots()
                        }
                    }
                }

                LANDSCAPE_SMALL, LANDSCAPE_MEDIUM, LANDSCAPE_EXPANDED -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))

                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.7f)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            DatePicker(state, onReadyForUpdate, onMonthChanged)
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.3f)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            TimeSlots()
                        }
                    }
                }
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    state: CreateBookingScreenState,
    onReadyForUpdate: () -> Unit,
    onMonthChanged: (input: Long) -> Unit
) {

    if (state.datesAreLoading) {
        LoadingIndicator()
    } else {

        DatePicker(
            state = state.datePickerState,
            title = null,
            headline = null,
            showModeToggle = false,
            modifier = Modifier.padding(0.dp)
        )

        LaunchedEffect(state.datePickerState.displayedMonthMillis) {
            if (!state.isUpdated)
                onMonthChanged(state.datePickerState.displayedMonthMillis)
            else
                onReadyForUpdate()
        }
    }
}

@Composable
fun TimeSlots() {
    InfoContainer(stringResource(R.string.select_date))
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PortraitPreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = PORTRAIT_SMALL
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 600,
    heightDp = 300,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapePreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = LANDSCAPE_SMALL
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 610,
    heightDp = 890,
    uiMode = Configuration.ORIENTATION_PORTRAIT
)
@Composable
fun PortraitMediumPreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = PORTRAIT_MEDIUM
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 830,
    heightDp = 482,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapeMediumPreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = LANDSCAPE_MEDIUM
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 600,
    heightDp = 1000,
    uiMode = Configuration.ORIENTATION_PORTRAIT
)
@Composable
fun PortraitExpandedPreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = PORTRAIT_EXPANDED
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    widthDp = 1000,
    heightDp = 480,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapeExpandedPreview() {
    AppTheme {
        CreateBookingScreen(
            CreateBookingScreenState(), {}, uiLayout = LANDSCAPE_EXPANDED
        )
    }
}




