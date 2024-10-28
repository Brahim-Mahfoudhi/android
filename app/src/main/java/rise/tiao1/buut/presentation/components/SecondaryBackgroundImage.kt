package rise.tiao1.buut.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import rise.tiao1.buut.R

@Composable
fun SecondaryBackgroundImage(){
    Image(
        painter = painterResource(R.drawable.buut_background_secondary),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}