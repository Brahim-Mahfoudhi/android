package rise.tiao1.buut.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import java.util.*

@Composable
fun DatePickerComponent(
    modifier: Modifier  = Modifier,
    value: String,
    onDatePicked: (String) -> Unit,
    onFocusLost: () -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    @StringRes label: Int,
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    OutlinedTextFieldComponent(
        value = value,
        onValueChanged = onDatePicked,
        onFocusLost = onFocusLost,
        label = label,
        isError = isError,
        errorMessage = errorMessage,
        readOnly = true,
        keyboardOptions = KeyboardOptions.Default,
        trailingIcon = {
            // Een knop om de DatePicker te openen
            IconButton(onClick = { isDialogOpen = true }) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Show calendar")
            }
        },
        modifier = modifier
    )



    // DatePickerDialog
    if (isDialogOpen) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Maak de DatePickerDialog
        DatePickerDialog(
            LocalContext.current,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Formatteer de datum zoals je wilt
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDatePicked(date) // Roep de callback aan met de geselecteerde datum
                isDialogOpen = false // Sluit de dialog
            },
            year, month, day
        ).show()
    }


}
