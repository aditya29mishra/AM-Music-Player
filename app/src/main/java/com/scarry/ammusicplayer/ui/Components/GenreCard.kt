package com.scarry.ammusicplayer.ui.Components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.scarry.ammusicplayer.Domain.Genre
import org.jetbrains.annotations.Async
@ExperimentalMaterialApi
@Composable
fun GenreCard(
    genre: Genre,
    @DrawableRes imageResourceId: Int,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = MaterialTheme.colors.surface
) {
    Card(
        modifier = modifier,
        onClick = onClick ?: {},
        backgroundColor = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .size(90.dp)
                    .offset(x = 110.dp, y = 30.dp)
                    .rotate(30f),
                painter = painterResource(id = imageResourceId),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                text = genre.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
        }
    }
}