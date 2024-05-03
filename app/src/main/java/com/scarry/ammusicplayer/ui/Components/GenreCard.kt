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
    onImageLoading: (() -> Unit)? = null,
    onImageLoadSuccess: (() -> Unit)? = null,
    onImageLoadFailed: ((Throwable) -> Unit)? = null,

){
    Card(
        modifier = modifier,
        onClick = onClick?:{}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(
                modifier = Modifier.placeholder(
                  isLoadingPlaceholderVisible,
                    highlight = PlaceholderHighlight.shimmer(),
                ),
                model = genre.coverArtURL.toString(),
                contentScale = ContentScale.Crop,
                onState = {
                    when (it){
                        is AsyncImagePainter.State.Empty -> {}
                        is AsyncImagePainter.State.Loading -> onImageLoading?.invoke()
                        is AsyncImagePainter.State.Success -> onImageLoadSuccess?.invoke()
                        is AsyncImagePainter.State.Error -> onImageLoadFailed?.invoke(it.result.throwable)
                    }
                },
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