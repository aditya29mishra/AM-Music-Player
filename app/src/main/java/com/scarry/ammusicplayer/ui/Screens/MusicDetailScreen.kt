package com.scarry.ammusicplayer.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.R
import com.scarry.ammusicplayer.ui.Components.AM_MusicPlayerCompactListItemCard
import com.scarry.ammusicplayer.ui.Components.AsyncImageWithPlaceholder

enum class MusicDetailScreenType {ALBUM, PLAYLIST}
@Composable
fun MusicDetailScreen(
    artUrl: String,
    musicDetailScreenType: MusicDetailScreenType,
    title: String,
    nameOfUpLoader: String,
    metadata: String,
    trackList: List<MusicSummary.TracKSummary>,
    onTrackItemClick: (MusicSummary.TracKSummary) -> Unit,
    onTrackTrailingButtonClick: (MusicSummary.TracKSummary) -> Unit,
    onBackButtonClick: () -> Unit,
    ){
    val metadataText = "${
        when (musicDetailScreenType) {
            MusicDetailScreenType.ALBUM -> "Album"
            MusicDetailScreenType.PLAYLIST -> "Playlist"
        }
    }•$metadata"
    var isLoadingPlaceholderVisible by remember { mutableStateOf(true)}
    LazyColumn (
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
    ){
        item{
            ArtWithHeader(
                artUrl = artUrl,
                title = title,
                nameOfUploader = nameOfUpLoader,
                metadata = metadataText,
                isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
                onAlbumArtLoading = { isLoadingPlaceholderVisible = true },
                onAlbumArtLoaded = { isLoadingPlaceholderVisible = false },
                onBackButtonClicked = onBackButtonClick
            )
        }
        items(trackList){
            AM_MusicPlayerCompactListItemCard(
                title = it.name,
                subtitle = it.nameOfArtist,
                onClick = { onTrackItemClick(it) },
                trailingButtonIcon = Icons.Default.MoreVert,
                onTrailingButtonIconClick = { onTrackTrailingButtonClick(it)},
                subtitleTextStyle = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors
                            .onBackground
                            .copy(ContentAlpha.disabled)
                        )
            )
        }
        item{
            Spacer(modifier = Modifier
                .navigationBarsHeight()
                .padding(bottom = 16.dp))
        }
    }
}
@Composable
fun ArtWithHeader(
    artUrl: String,
    title: String,
    nameOfUploader: String,
    metadata: String,
    isLoadingPlaceholderVisible: Boolean = false,
    onAlbumArtLoading: () -> Unit,
    onAlbumArtLoaded: (Throwable?) -> Unit,
    onBackButtonClicked: () -> Unit
) {
    Box(modifier =  Modifier.fillMaxSize()){
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .background(MaterialTheme.colors.background.copy(alpha = 0.5f))
                .padding(8.dp),
            onClick = onBackButtonClicked
        ) {
           Icon(
               imageVector = ImageVector.vectorResource(id = R.drawable.baseline_chevron_left_24) ,
               contentDescription =null
           )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImageWithPlaceholder(
                model = artUrl,
                contentDescription = null,
                onImageLoadingFinished = onAlbumArtLoaded,
                onImageLoading = onAlbumArtLoading,
                isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = nameOfUploader,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = metadata,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography
                    .subtitle2
                    .copy(
                        color = MaterialTheme.colors
                            .onBackground
                            .copy(alpha = ContentAlpha.medium)
                    )
            )
        }
    }
}
