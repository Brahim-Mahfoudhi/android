package rise.tiao1.buut.presentation.booking

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R
import rise.tiao1.buut.domain.booking.Booking
import rise.tiao1.buut.utils.toDateString

@Composable
fun BookingItem(
    item: Booking
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.88f

    Card(elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(itemWidth),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.light_gray_blue))

    ) {
        Text(text = stringResource(R.string.date_of_booking) + ": ${item.date.toDateString()}",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp).fillMaxWidth()

        ) {

            BookingDetails(
                item.numberOfAdults,
                item.numberOfChildren,
                item.boat,
                item.battery,
            )

        }
    }
}

@Composable
fun BookingDetails(
    numberOfAdults: Int,
    numberOfChildren: Int,
    boat: String? = null,
    battery: String? = null,
    modifier: Modifier = Modifier,

) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Text(text = stringResource(R.string.number_of_adults) + ": $numberOfAdults",
            modifier = Modifier.alpha(0.6f))
        Text(text = stringResource(R.string.number_of_children) + ": $numberOfChildren",
            modifier = Modifier.alpha(0.6f))
    }

    if (boat != null) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = stringResource(R.string.boat) + ": $boat",
                modifier = Modifier.alpha(0.6f))
            Text(text = stringResource(R.string.battery) + ": $battery",
                modifier = Modifier.alpha(0.6f))
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        IconButton(onClick = { /* TODO Edit Booking */ }) {
            Icon(imageVector = Icons.Filled.Edit , contentDescription = "Edit booking")
        }
        IconButton(onClick = { /* TODO Delete Booking */ }) {
            Icon(imageVector = Icons.Filled.DeleteOutline , tint = Color.Red,  contentDescription = "Delete booking")
        }
    }
}