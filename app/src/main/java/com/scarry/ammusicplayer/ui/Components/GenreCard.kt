package com.scarry.ammusicplayer.ui.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenreCard(
    genre: Genre,
    modifier: Modifier = Modifier,
    isLoadingPlaceholderVisible: Boolean = false,
    onClick: (() -> Unit)? = null,
    onImageLoading: () -> Unit,
    onImageLoadingFinished: (Throwable?) -> Unit

){
    Card(
        modifier = modifier,
        onClick = onClick?:{}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImageWithPlaceholder(
                model = genre.coverArtURL.toString(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
                onImageLoading = onImageLoading,
                onImageLoadingFinished = onImageLoadingFinished
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