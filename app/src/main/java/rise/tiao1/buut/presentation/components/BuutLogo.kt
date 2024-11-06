package rise.tiao1.buut.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rise.tiao1.buut.R

@Composable
fun BuutLogo(){
    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.buut_logo),
            contentDescription = "BuutLogo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp)
        )
    }
}