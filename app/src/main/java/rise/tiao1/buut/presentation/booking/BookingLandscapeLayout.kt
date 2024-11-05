package rise.tiao1.buut.presentation.booking

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rise.tiao1.buut.R
import rise.tiao1.buut.presentation.components.ButtonComponent
import rise.tiao1.buut.presentation.components.HeaderOne
import rise.tiao1.buut.presentation.components.MainBackgroundImage
import rise.tiao1.buut.ui.theme.AppTheme
import rise.tiao1.buut.utils.toDateString
import java.time.LocalDate
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingLandscapeLayout(
    state: BookingScreenState,
    onValueChanged: (input: Long?) -> Unit,
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
        MainBackgroundImage()
         Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)

    ) {
        DatePicker(
            modifier = Modifier.scale(0.9f),
            state = datePickerState,
            showModeToggle = false,
            title = null,
            headline = null
        )
    }
    Spacer(modifier = Modifier.widthIn(20.dp))
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        HeaderOne(text = stringResource(R.string.make_reservation) + " ${state.selectedDate?.toDateString()}")
        Spacer(modifier = Modifier.heightIn(12.dp))

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




@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    widthDp = 640, // Optioneel: geef breedte op voor landscape-weergave
    heightDp = 360
)// Optioneel: geef hoogte op voor landscape-weergave)
@Composable
fun DefaultPreview() {
    AppTheme {
        BookingLandscapeLayout(BookingScreenState(), { _ -> {} })
    }
}
