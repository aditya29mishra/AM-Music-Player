package com.scarry.ammusicplayer.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.scarry.ammusicplayer.R

enum class ListItemCardType { ALBUM, ARTIST, SONG, PLAYLIST }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AM_MusicPlayerCompactListItemCard(
    thumbnailImageURLString: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    trailingButton: ImageVector,
    onTrailingButtonClick: () -> Unit,
    titleTextStyle: TextStyle = LocalTextStyle.current,
    subtitleTextStyle: TextStyle = LocalTextStyle.current,
    isLoadingPlaceholderVisible: Boolean = false,
    onThumbnailLoading: (()-> Unit)? = null,
    onThumbnailLoadSuccess: (() -> Unit)? = null,
    onThumbnailLoadFailure: (() -> Unit)? = null,
    placeholderHighlight: PlaceholderHighlight = PlaceholderHighlight.shimmer(),
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            AsyncImage(
                model = thumbnailImageURLString,
                contentDescription = null,
                modifier = Modifier
                    .size(75.dp)
                    .placeholder(
                        visible = isLoadingPlaceholderVisible,
                        highlight = placeholderHighlight
                    ),
                contentScale = ContentScale.Crop,
                onState = {
                    when (it) {
                        is AsyncImagePainter.State.Empty -> {}
                        is AsyncImagePainter.State.Loading -> onThumbnailLoading?.invoke()
                        is AsyncImagePainter.State.Success -> onThumbnailLoadSuccess?.invoke()
                        is AsyncImagePainter.State.Error -> onThumbnailLoadFailure?.invoke()

                    }
                }
            )
            Column (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = titleTextStyle

                )
                Text(
                    text = subtitle,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = subtitleTextStyle
                )
            }
            IconButton(onClick = onTrailingButtonClick) {
                Icon(
                    imageVector =trailingButton ,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun AM_MusicPlayerCompactListItemCard(
    cardType: ListItemCardType,
    thumbnailImageUrlString: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    onTrailingButtonIconClick: () -> Unit,
    titleTextStyle: TextStyle = LocalTextStyle.current,
    subtitleTextStyle: TextStyle = LocalTextStyle.current,
    isLoadingPlaceHolderVisible: Boolean = false,
    onThumbnailLoading: (() -> Unit)? = null,
    onThumbnailLoadFailure: (() -> Unit)? = null,
    onThumbnailLoadSuccess: (() -> Unit)? = null,
    placeholderHighlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()

){
    AM_MusicPlayerCompactListItemCard(
        thumbnailImageURLString = thumbnailImageUrlString,
        title = title,
        subtitle = subtitle,
        onClick = onClick,
        trailingButton = when (cardType){
            ListItemCardType.SONG -> Icons.Filled.MoreVert
            else -> ImageVector.vectorResource(id = R.drawable.baseline_chevron_right_24)
        },
        onTrailingButtonClick = onTrailingButtonIconClick,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle,
        isLoadingPlaceholderVisible = isLoadingPlaceHolderVisible,
        onThumbnailLoading = onThumbnailLoading,
        onThumbnailLoadFailure = onThumbnailLoadFailure,
        onThumbnailLoadSuccess = onThumbnailLoadSuccess,
        placeholderHighlight = placeholderHighlight
    )
}

