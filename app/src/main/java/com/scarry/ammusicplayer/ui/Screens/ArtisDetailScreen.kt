package com.scarry.ammusicplayer.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.ui.Components.AM_MusicPlayerCompactListItemCard
import com.scarry.ammusicplayer.ui.Components.AsyncImageWithPlaceholder
import com.scarry.ammusicplayer.ui.Components.ListItemCardType

@Composable
fun ArtisDetailScreen(
    artistSummary: MusicSummary.ArtistSummary,
    popularTrack: List<MusicSummary.TrackSummary>,
    popularReleases: List<MusicSummary.AlbumSummary>,
    onBackButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onTrackClicked: (MusicSummary.TrackSummary) -> Unit,
    onTrackTrailingButtonIconClicked : (MusicSummary.TrackSummary) -> Unit,
    onAlbumClicked: (MusicSummary.AlbumSummary) -> Unit
) {
    val subtitleTextColorWithAlpha = MaterialTheme.colors.onBackground.copy(
        alpha = ContentAlpha.disabled
    )
    var isCoverArtPlaceholderVisible by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        artistCoverArtHeaderItem(
            artistName = artistSummary.name,
            artistCoverArtUrlString = artistSummary.associatedImageUrl.toString(),
            onBackButtonClicked = onBackButtonClicked,
            onPlayButtonClicked = onPlayButtonClicked,
            isLoadingPlaceholderVisible = isCoverArtPlaceholderVisible,
            onCoverArtLoading = { isCoverArtPlaceholderVisible = true },
            isCoverArtLoaded = { isCoverArtPlaceholderVisible = false }

        )
        items(popularTrack) {
            AM_MusicPlayerCompactListItemCard(
                modifier = Modifier
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                cardType = ListItemCardType.SONG,
                thumbnailImageUrlString = it.associatedImageUrl.toString(),
                title = it.name,
                subtitle = "${it.numberOfPlays} plays",
                subtitleTextStyle = MaterialTheme.typography.caption.copy(
                    color = subtitleTextColorWithAlpha
                ),
                onClick = { onTrackClicked(it) },
                onTrailingButtonIconClick = { onTrackTrailingButtonIconClicked(it) }
            )
        }
        item{
            SubtitleText(
                modifier = Modifier.padding(start = 16.dp),
                text = "Popular tracks"
            )
        }
        items(popularReleases){
            AM_MusicPlayerCompactListItemCard(
                modifier = Modifier
                    .height(80.dp)
                    .padding(horizontal = 16.dp),
                cardType = ListItemCardType.ALBUM,
                thumbnailImageUrlString = it.associatedImageUrl.toString(),
                title = it.name,
                titleTextStyle = MaterialTheme.typography.h6,
                subtitle =  it.yearOfReleaseString,
                subtitleTextStyle = MaterialTheme.typography.caption.copy(
                    color = subtitleTextColorWithAlpha
                ),
                onClick = { onAlbumClicked(it) },
                onTrailingButtonIconClick = { onAlbumClicked(it) }
            )
        }
        item{
            Spacer(modifier = Modifier.navigationBarsHeight())
        }
    }
}

@Composable
fun SubtitleText(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold,
    )
}

fun LazyListScope.artistCoverArtHeaderItem(
    artistName: String,
    artistCoverArtUrlString: Any,
    onBackButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    isLoadingPlaceholderVisible: Boolean = false,
    onCoverArtLoading: (() -> Unit) ? =  null,
    isCoverArtLoaded: ((Throwable?) -> Unit)? = null
) {
   item {
       Box (
           modifier = Modifier
               .fillParentMaxHeight(0.6f)
               .fillParentMaxWidth()
       ){
           AsyncImageWithPlaceholder(
               modifier = Modifier.fillMaxSize(),
               model = artistCoverArtUrlString,
               contentDescription = null,
               contentScale = ContentScale.Crop,
               isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
               onImageLoading = { onCoverArtLoading?.invoke() },
               onImageLoadingFinished = { isCoverArtLoaded?.invoke(it) }
           )
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .background(Color.Black.copy(alpha = 0.2f))
           )
           IconButton(
               modifier = Modifier
                   .align(Alignment.TopStart)
                   .statusBarsPadding()
                   .padding(18.dp)
                   .size(50.dp)
                   .clip(CircleShape)
                   .background(MaterialTheme.colors.background.copy(alpha = 0.5f)),
               onClick = onBackButtonClicked
           ) {
               Icon(
                   imageVector = Icons.Filled.ArrowBack,
                   contentDescription = null
               )
               Text(
                   modifier = Modifier
                       .align(Alignment.BottomStart)
                       .padding(16.dp),
                   text = artistName,
                   style = MaterialTheme.typography.h3,
                   fontWeight = FontWeight.Bold,
                   maxLines = 2,
                   overflow = TextOverflow.Ellipsis
               )
               FloatingActionButton(
                   modifier = Modifier
                       .align(Alignment.BottomEnd)
                       .padding(end = 16.dp)
                       .offset(y = 24.dp),
                   backgroundColor = MaterialTheme.colors.primary,
                   onClick = onPlayButtonClicked
               ) {
                   Icon(
                       modifier = Modifier.size(50.dp),
                       imageVector = Icons.Filled.PlayArrow,
                       contentDescription = null
                   )
               }
           }
       }
   }
}
