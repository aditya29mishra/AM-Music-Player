package com.scarry.ammusicplayer.ui.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.ui.Components.AM_MusicPlayerCompactListItemCard
import com.scarry.ammusicplayer.ui.Components.GenreCard
import com.scarry.ammusicplayer.ui.Components.ListItemCardType

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    genreList: List<Genre>,
    onGenreItemClick: (Genre) -> Unit,
    onSearchTextChanged: (String) -> Unit,
    searchQueryResult: List<MusicSummary>,
    onSearchQueryItemClicked: (MusicSummary) -> Unit,
    onSearchQueryItemTrailingIconButtonClicked: (MusicSummary) -> Unit

) {
    var searchText by remember { mutableStateOf("") }
    val isLoadingMap = remember { mutableStateMapOf<String, Boolean>() }
    var isSearchListVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    BackHandler(isSearchListVisible) {
        searchText = ""
        focusManager.clearFocus()
        isSearchListVisible = false
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Search",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) isSearchListVisible = true
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = "Artist, Song or Album",
                    fontWeight = FontWeight.SemiBold
                )
            },
            singleLine = true,
            value = searchText,
            onValueChange = {
                searchText = it
                onSearchTextChanged(it)
            },
            textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = Color.Black,
                placeholderColor = Color.Black,
                textColor = Color.Black
            )
        )
        Box {
            Text(
                text = "Genres",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(170.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = genreList) {
                    GenreCard(
                        genre = it,
                        modifier = Modifier.height(120.dp),
                        isLoadingPlaceholderVisible = isLoadingMap.getOrPut(it.id) { true },
                        onClick = { onGenreItemClick(it) },
                        onImageLoading = { isLoadingMap[it.id] = true },
                        onImageLoadingFinished = { _ -> isLoadingMap[it.id] = false }
                    )
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = isSearchListVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SearchQueryList(
                    searchQueryResult = searchQueryResult,
                    onItemClick = {onSearchQueryItemClicked(it)},
                    onTrailingIconButtonClick = {onSearchQueryItemTrailingIconButtonClicked(it)}
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SearchQueryList(
    searchQueryResult: List<MusicSummary>,
    onItemClick: (MusicSummary) -> Unit,
    onTrailingIconButtonClick: (MusicSummary) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(searchQueryResult) {
            AM_MusicPlayerCompactListItemCard(
                cardType = it.getAssociatedListCardType(),
                thumbnailImageUrlString = it.associatedImageUrl.toString(),
                title = it.name,
                subtitle = it.associatedMetadata ?: "",
                onClick = { onItemClick(it) },
                onTrailingButtonIconClick = { onTrailingIconButtonClick(it) }
            )
        }
    }
}

private fun MusicSummary.getAssociatedListCardType(): ListItemCardType = when (this) {
    is MusicSummary.AlbumSummary -> ListItemCardType.ALBUM
    is MusicSummary.ArtistSummary -> ListItemCardType.ARTIST
    is MusicSummary.PlaylistSummary -> ListItemCardType.PLAYLIST
    is MusicSummary.TrackSummary -> ListItemCardType.SONG
}