package rise.tiao1.buut.presentation.booking

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.presentation.profile.ProfileScreenState

@Composable
fun BookingList(
    state: ProfileScreenState
){

        LazyRow {
            items(state.bookings){ booking ->
                BookingItem(
                    booking
                )
            }
        }

}
