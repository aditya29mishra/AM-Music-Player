package com.scarry.ammusicplayer.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.scarry.ammusicplayer.R
import com.scarry.ammusicplayer.utils.conditional

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
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = LocalTextStyle.current,
    subtitleTextStyle: TextStyle = LocalTextStyle.current,
    thumbnailImageUrlString : String? = null,
    thumbnailShape: Shape? = null,
    isLoadingPlaceholderVisible: Boolean = false,
    onThumbnailLoading: (()-> Unit)? = null,
    onThumbnailImageLoadingFinished: ((Throwable?) -> Unit)? = null,
    placeholderHighlight: PlaceholderHighlight = PlaceholderHighlight.shimmer(),
){
    Card(
        modifier = Modifier
            .sizeIn(minHeight = 56.dp, minWidth = 250.dp, maxHeight = 80.dp)
            .then(modifier),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            thumbnailImageUrlString?.let {
                AsyncImageWithPlaceholder(
                    model = thumbnailImageURLString,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f, true)
                        .conditional(thumbnailShape != null) { clip(shape = thumbnailShape!!) },
                    isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
                    onImageLoading = { onThumbnailLoading?.invoke() },
                    onImageLoadingFinished = { onThumbnailImageLoadingFinished?.invoke(it) },
                    placeholderHighlight = placeholderHighlight,
                    contentScale = ContentScale.Crop,
                )
            }
            Column (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .padding(start = 16.dp),
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
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = onTrailingButtonClick
            ) {
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
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = LocalTextStyle.current,
    subtitleTextStyle: TextStyle = LocalTextStyle.current,
    isLoadingPlaceHolderVisible: Boolean = false,
    onThumbnailLoading: (() -> Unit)? = null,
    onThumbnailImageLoadingFinished: ((Throwable?) -> Unit)? = null,
    placeholderHighlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()

){
    AM_MusicPlayerCompactListItemCard(
        modifier = Modifier,
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
        thumbnailShape = if (cardType == ListItemCardType.ARTIST) CircleShape else null,
        isLoadingPlaceholderVisible = isLoadingPlaceHolderVisible,
        onThumbnailLoading = onThumbnailLoading,
        onThumbnailImageLoadingFinished = onThumbnailImageLoadingFinished,
        placeholderHighlight = placeholderHighlight
    )
}

