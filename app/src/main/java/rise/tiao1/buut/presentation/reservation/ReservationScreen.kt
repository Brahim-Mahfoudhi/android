package rise.tiao1.buut.presentation.reservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.CustomSelectableDates
import rise.tiao1.buut.presentation.components.datePicker
import rise.tiao1.buut.presentation.components.toMillis
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.toDateString
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    state: ReservationScreenState,
    onValueChanged: (input: Long?) -> Unit
) {

    val datePickerState = remember {
        DatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            yearRange = (LocalDate.now().year..LocalDate.now().plusYears(1L).year),
            locale = Locale.US,
            selectableDates = state.nonSelectableDates,
        )
    }
    onValueChanged(datePickerState.selectedDateMillis)

    Box {
        Image(
            painter = painterResource(R.drawable.buut_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= stringResource(R.string.make_reservation_title) + " ${state.selectedDate?.toDateString()}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(top= 20.dp)

            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    title = null,
                    headline = null
                    )
            }
            Spacer(modifier = Modifier.heightIn(20.dp))
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(color=Color.White)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center,

                ) {

                ButtonComponent(
                    label = R.string.morning_label,
                    onClick = {}
                )
                Spacer(modifier = Modifier.heightIn(12.dp))
                ButtonComponent(
                    label = R.string.afternoon_label,
                    onClick = {}
                )
                Spacer(modifier = Modifier.heightIn(12.dp))
                ButtonComponent(
                    label = R.string.evening_label,
                    onClick = {}
                )
            }

        }
    }
    }




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        ReservationScreen(ReservationScreenState(), {_ -> {}})
    }
}

