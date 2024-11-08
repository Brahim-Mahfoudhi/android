package rise.tiao1.buut.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.presentation.components.HeaderOne
import rise.tiao1.buut.presentation.components.Navigation
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.NavigationKeys
import rise.tiao1.buut.utils.UiLayout
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_EXPANDED
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_MEDIUM
import rise.tiao1.buut.utils.UiLayout.LANDSCAPE_SMALL
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_EXPANDED
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_MEDIUM
import rise.tiao1.buut.utils.UiLayout.PORTRAIT_SMALL
import rise.tiao1.buut.utils.toDateString
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    logout: () -> Unit,
    navigateTo: (String) -> Unit,
    uiLayout: UiLayout
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.image_size_standard)),
                            painter = painterResource(R.drawable.buut_logo),
                            contentDescription = stringResource(R.string.buut_logo)
                        )
                    }
                },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            )
        },
        bottomBar = {
            if (uiLayout == PORTRAIT_SMALL) {
                Navigation(
                    logout = logout,
                    navigateTo = navigateTo,
                    uiLayout = PORTRAIT_SMALL,
                    currentPage = NavigationKeys.Route.HOME
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiLayout) {
                PORTRAIT_SMALL -> {
                    Column (
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    ) {
                        HeaderOne(stringResource(R.string.booking_list_title))
                        BookingList(state = state)
                    }
                }

                PORTRAIT_EXPANDED, LANDSCAPE_EXPANDED -> {
                    Row {
                        Navigation(
                            uiLayout = uiLayout,
                            navigateTo = navigateTo,
                            logout = logout,
                            currentPage = NavigationKeys.Route.HOME,
                            content = {
                                Column (
                                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                                ) {
                                    HeaderOne(stringResource(R.string.booking_list_title))
                                    BookingList(state = state)
                                }
                            },
                        )
                    }
                }

                else -> {
                    Row {
                        Navigation(
                            uiLayout = uiLayout,
                            navigateTo = navigateTo,
                            logout = logout,
                            currentPage = NavigationKeys.Route.HOME
                        )
                        Column  (
                            modifier = Modifier.padding(horizontal =  dimensionResource(R.dimen.padding_medium)),
                        ) {
                            HeaderOne(stringResource(R.string.booking_list_title))
                            BookingList(state = state)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingList(
    state: HomeScreenState
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (state.isLoading) {
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
    } else if (!state.apiError.isNullOrBlank()) {
        Surface(
            color = MaterialTheme.colorScheme.errorContainer,
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
                    imageVector = Icons.Filled.WarningAmber,
                    contentDescription = stringResource(R.string.warning_icon),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = state.apiError,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center,
                )

            }
        }
    } else if (state.bookings.isEmpty()) {
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
                    text = stringResource(R.string.user_has_no_bookings),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    } else {

        val firstUpcomingBookingIndex = state.bookings.indexOfFirst {
            it.date.isAfter(LocalDateTime.now().minusDays(1))
        }

        LaunchedEffect(firstUpcomingBookingIndex) {
            if (firstUpcomingBookingIndex >= 0) {
                coroutineScope.launch {
                    lazyListState.scrollToItem(firstUpcomingBookingIndex, scrollOffset = 0)
                }
            }
        }

        LazyColumn(state = lazyListState) {
            itemsIndexed(state.bookings) { index, booking ->
                BookingItem(
                    item = booking,
                    isExpanded = index == firstUpcomingBookingIndex,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_tiny))
                )
            }
        }

    }
}

@Composable
fun BookingItem(
    item: Booking,
    isExpanded: Boolean = false,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(isExpanded) }
    val isHistory = item.date.isBefore(LocalDateTime.now().minusDays(1))

    Card(
        modifier = modifier
            .alpha(if (isHistory) 0.5f else 1f)
            .semantics { testTag = if (isHistory) "PastBooking" else "UpcomingBooking" }
            .semantics { testTag = "BookingItem" }
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_small)
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.date.toDateString(),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.semantics { this.testTag = "ExpandButton" }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = stringResource(R.string.expand_button_content_description),
                    )
                }

            }
            if (expanded) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    BookingDetails(
                        item.boat,
                        item.battery,
                    )
                }
            }
        }
    }
}

@Composable
fun BookingDetails(
    boat: String? = null,
    battery: String? = null,
) {
    Column {
        Text(
            text = if (boat != null) stringResource(R.string.boat) + ": $boat" else stringResource(
                R.string.no_boat_assigned
            ),
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            text = if (battery != null) stringResource(R.string.battery) + ": $battery" else stringResource(
                R.string.no_battery_assigned
            ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

private fun getPreviewHomeScreenState(emptyList: Boolean = false): HomeScreenState {
    return HomeScreenState(
        null, if (emptyList) emptyList() else listOf(
            Booking(
                date = LocalDateTime.now().minusDays(2),
                boat = "Boat 0",
                battery = "Battery Z"
            ),
            Booking(
                date = LocalDateTime.now(),
                boat = "Boat 1",
                battery = "Battery A"
            ),
            Booking(
                date = LocalDateTime.now().plusDays(1),
                boat = "Boat 2",
                battery = "Battery B"
            ),
            Booking(
                date = LocalDateTime.now().plusDays(2),
                boat = "Boat 3",
                battery = "Battery C"
            )
        ), false
    )
}

@Preview(showBackground = true)
@Composable
fun PortraitPreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(true), {}, {}, PORTRAIT_SMALL
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 600,
    heightDp = 300,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapePreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(), {}, {}, LANDSCAPE_SMALL
        )
    }
}


@Preview(
    showBackground = true,
    widthDp = 610,
    heightDp = 890,
    uiMode = Configuration.ORIENTATION_PORTRAIT
)
@Composable
fun PortraitMediumPreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(), {}, {}, PORTRAIT_MEDIUM
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 830,
    heightDp = 482,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapeMediumPreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(), {}, {}, LANDSCAPE_MEDIUM
        )
    }
}


@Preview(
    showBackground = true,
    widthDp = 600,
    heightDp = 1000,
    uiMode = Configuration.ORIENTATION_PORTRAIT
)
@Composable
fun PortraitExpandedPreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(), {}, {}, PORTRAIT_EXPANDED
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 1000,
    heightDp = 480,
    uiMode = Configuration.ORIENTATION_LANDSCAPE
)
@Composable
fun LandscapeExpandedPreview() {
    AppTheme {
        HomeScreen(
            getPreviewHomeScreenState(), {}, {}, LANDSCAPE_EXPANDED
        )
    }
}

