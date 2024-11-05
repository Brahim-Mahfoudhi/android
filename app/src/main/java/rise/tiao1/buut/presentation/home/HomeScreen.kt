package rise.tiao1.buut.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.api.ResourceDescriptor.History
import kotlinx.coroutines.launch
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.BuutLogo
import rise.tiao1.buut.presentation.components.HeaderOne
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.UiLayout
import rise.tiao1.buut.utils.toDateString
import java.time.LocalDateTime


@Composable
fun HomeScreen(
    state: HomeScreenState,
    logout: () -> Unit,
    toReservationPage: () -> Unit,
    uiLayout: UiLayout
) {
    Box {
        when (uiLayout) {
            UiLayout.PORTRAIT_SMALL -> {
                Column {
                    HeaderOne(state.apiError ?: "")
                    Spacer(modifier = Modifier.heightIn(8.dp))
                    HeaderOne(stringResource(R.string.booking_list_title))
                    BookingList(state = state)
                    Spacer(modifier = Modifier.heightIn(8.dp))
                }
            }

            UiLayout.PORTRAIT_LARGE -> {
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f) // Pas hier de weight toe
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        navigationTemporary(logout = logout, toReservationPage = toReservationPage)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .weight(5f) // Pas hier de weight toe
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        HeaderOne(state.apiError ?: "")
                        Spacer(modifier = Modifier.heightIn(8.dp))
                        HeaderOne(stringResource(R.string.booking_list_title))
                        BookingList(state = state)
                    }
                }
            }

            else -> {
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f) // Pas hier de weight toe
                            .padding(8.dp)
                    ) {
                        navigationTemporary(logout = logout, toReservationPage = toReservationPage)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier
                            .weight(5f) // Pas hier de weight toe
                            .padding(8.dp)
                    ) {
                        HeaderOne(stringResource(R.string.booking_list_title))
                        BookingList(state = state)
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
    } else if (state.bookings.isEmpty()) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

                Text(
                    text = stringResource(R.string.user_has_no_bookings),
                    color = Color.Black,
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
                    isExpanded = index == firstUpcomingBookingIndex // Open de eerstvolgende boeking
                )
            }
        }

    }
}

@Composable
fun BookingItem(
    item: Booking,
    isExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(isExpanded) }
    val isHistory = item.date.isBefore(LocalDateTime.now().minusDays(1))

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .alpha(if (isHistory) 0.5f else 1f)
            .semantics { testTag = if (isHistory) "PastBooking" else "UpcomingBooking" }
            .semantics { testTag = "BookingItem" }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.date.toDateString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.semantics { this.testTag = "ExpandButton" }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = stringResource(R.string.expand_button_content_description),
                        tint = Color.Black
                    )
                }

            }
            if (expanded) {
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 2.dp, vertical = 8.dp)

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (boat != null) stringResource(R.string.boat) + ": $boat" else stringResource(
                    R.string.no_boat_assigned
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = if (battery != null) stringResource(R.string.battery) + ": $battery" else stringResource(
                    R.string.no_battery_assigned
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
            )
        }
    }
}


@Composable
fun navigationTemporary(
    logout: () -> Unit,
    toReservationPage: () -> Unit,
) {

    BuutLogo()
    ButtonComponent(
        label = R.string.make_reservation,
        onClick = { toReservationPage() }
    )
    ButtonComponent(
        label = R.string.log_out_button,
        onClick = { logout() }
    )
}

@Preview()
@Composable
fun DefaultPreview() {
    AppTheme {
        HomeScreen(HomeScreenState(
            null, listOf(
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
        ), {}, {}, UiLayout.PORTRAIT_SMALL
        )
    }
}

