package com.example.justmovie.util.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.justmovie.util.Constants.Companion.MOVIE_IMAGE_POSTER_SIZE_W500

@Composable
fun ResourceCellUi(
    modifier: Modifier = Modifier,
    imagePath: String = "",
    title: String = "",
    onClick: () -> Unit = {}
){
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("${MOVIE_IMAGE_POSTER_SIZE_W500}${imagePath}")
                .crossfade(true)
                .build(),
            contentDescription = "$title Poster",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() },
            contentScale = ContentScale.Crop
        )
    }
}