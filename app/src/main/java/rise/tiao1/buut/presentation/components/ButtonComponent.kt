package rise.tiao1.buut.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rise.tiao1.buut.ui.theme.Primary

@Composable
fun ButtonComponent(
    @StringRes label: Int,
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            containerColor = Primary,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(
            text = stringResource(label),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

    }
}