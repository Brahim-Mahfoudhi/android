package rise.tiao1.buut.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.booking.BookingList
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.BuutLogo
import rise.tiao1.buut.presentation.components.ErrorMessageContainer
import rise.tiao1.buut.presentation.components.SecondaryBackgroundImage


@Composable
fun ProfileScreen(state: ProfileScreenState, logout: () -> Unit, toReservationPage: () -> Unit) {
    Box {
        SecondaryBackgroundImage()
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        BuutLogo()
        Text(
            text = stringResource(R.string.initial_title)
        )
        if (state.isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(120.dp),
                    color = Color.Black,
                    strokeWidth = 5.dp
                )

        } else if (state.apiError.isNotBlank()) {
            ErrorMessageContainer(state.apiError)
        } else {
            UserInfoRow(
                label = stringResource(R.string.name_label),
                value = state.user?.firstName,
            )
            UserInfoRow(
                label = stringResource(R.string.email_label),
                value = state.user?.email,
            )
        }
        BookingList(state = state)
        ButtonComponent(
            label = R.string.make_reservation,
            onClick = { toReservationPage() },
        )
        ButtonComponent(
            label = R.string.log_out_button,
            onClick = { logout() },
        )
        }
}
    }


@Composable
fun UserInfoRow(label: String, value: String?) {
    Row {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer(
            modifier = Modifier.width(10.dp),
        )
        Text(
            text = value ?: "" ,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        )
    }
}