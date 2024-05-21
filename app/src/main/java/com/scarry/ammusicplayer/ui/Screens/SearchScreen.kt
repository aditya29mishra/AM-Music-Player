package com.scarry.ammusicplayer.ui.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.navigationBarsHeight
import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.R
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
    isSearchResultLoading: Boolean,
    searchQueryResult: SearchResults,
    onSearchQueryItemClicked: (SearchResult) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    val isGenreImageLoadingMap = remember { mutableStateMapOf<String, Boolean>() }
    var isSearchListVisible by remember { mutableStateOf(false) }
    val isClearSearchTextButtonVisible by remember { derivedStateOf{ isSearchListVisible && searchText.isNotEmpty() } }
    val focusManager = LocalFocusManager.current
    val textFieldTrailingButton = @Composable {
        AnimatedVisibility(
            visible = isSearchListVisible,
            enter = fadeIn() + slideInHorizontally { it },
            exit = slideOutHorizontally{it} + fadeOut()
        ) {
            IconButton(
                onClick = {
                    searchText = " "
                onSearchTextChanged("")
                },
                content = { Icon(imageVector = Icons.Filled.Close, contentDescription = null) }
            )
        }
    }
    val isSearchItemLoadingPlaceHolderVisibleMap =
        remember { mutableStateMapOf<SearchResult, Boolean>() }
    val isSearchResultLoadingAnimationcomposition by rememberLottieComposition(
        spec =  LottieCompositionSpec.RawRes(R.raw.lottie_loading_anim)
    )
    BackHandler(isSearchListVisible) {
        focusManager.clearFocus()
        if(searchText.isEmpty())isSearchListVisible = false
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
                    if (it.isFocused) {
                        isSearchListVisible = true
                    }
                },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = textFieldTrailingButton,
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
                trailingIconColor = Color.Black,
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
                        isLoadingPlaceholderVisible = isGenreImageLoadingMap.getOrPut(it.id) { true },
                        onClick = { onGenreItemClick(it) },
                        onImageLoading = { isGenreImageLoadingMap[it.id] = true },
                        onImageLoadingFinished = { _ -> isGenreImageLoadingMap[it.id] = false }
                    )
                }
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = isSearchListVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SearchQueryList(
                    searchResults = searchQueryResult,
                    onItemClick = { onSearchQueryItemClicked(it) },
                    onTrailingIconButtonClick = { },
                    isLoadingPlaceHolderVisible = { item ->
                        isSearchItemLoadingPlaceHolderVisibleMap.getOrPut(item) { false }
                    },
                    onImageLoadingFinished = { item, _ ->
                        isSearchItemLoadingPlaceHolderVisibleMap[item] = false
                    },
                    onImageLoading = { isSearchItemLoadingPlaceHolderVisibleMap[it] = true },
                    isSearchResultLoadingAnimationVisible = isSearchResultLoading,
                    lottieComposition = isSearchResultLoadingAnimationcomposition
                    )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun SearchQueryList(
    searchResults: SearchResults,
    onItemClick: (SearchResult) -> Unit,
    onTrailingIconButtonClick: (SearchResult) -> Unit,
    isLoadingPlaceHolderVisible: (SearchResult) -> Boolean,
    onImageLoading: (SearchResult) -> Unit,
    onImageLoadingFinished: (SearchResult, Throwable?) -> Unit,
    isSearchResultLoadingAnimationVisible: Boolean = false,
    lottieComposition: LottieComposition?,
) {
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(searchResults.tracks) {
                AM_MusicPlayerCompactListItemCard(
                    cardType = it.getAssociatedListCardType(),
                    thumbnailImageUrlString = it.imageUrlString,
                    title = it.name,
                    subtitle = it.artistsString,
                    onClick = { onItemClick(it) },
                    onTrailingButtonIconClick = { onTrailingIconButtonClick(it) },
                    isLoadingPlaceHolderVisible = isLoadingPlaceHolderVisible(it),
                    onThumbnailImageLoadingFinished = { throwable ->
                        onImageLoadingFinished(it, throwable)
                    },
                    onThumbnailLoading = { onImageLoading(it) }
                )
            }
            items(searchResults.albums) {
                AM_MusicPlayerCompactListItemCard(
                    cardType = it.getAssociatedListCardType(),
                    thumbnailImageUrlString = it.albumArtUrlString,
                    title = it.name,
                    subtitle = it.artistsString,
                    onClick = { onItemClick(it) },
                    onTrailingButtonIconClick = { onTrailingIconButtonClick(it) },
                    isLoadingPlaceHolderVisible = isLoadingPlaceHolderVisible(it),
                    onThumbnailImageLoadingFinished = { throwable ->
                        onImageLoadingFinished(it, throwable)
                    },
                    onThumbnailLoading = { onImageLoading(it) }
                )
            }
            items(searchResults.artists) {
                AM_MusicPlayerCompactListItemCard(
                    cardType = it.getAssociatedListCardType(),
                    thumbnailImageUrlString = it.imageUrlString ?: "",
                    title = it.name,
                    subtitle = "Artist",
                    onClick = { onItemClick(it) },
                    onTrailingButtonIconClick = { onTrailingIconButtonClick(it) },
                    isLoadingPlaceHolderVisible = isLoadingPlaceHolderVisible(it),
                    onThumbnailImageLoadingFinished = { throwable ->
                        onImageLoadingFinished(it, throwable)
                    },
                    onThumbnailLoading = { onImageLoading(it) }
                )
            }
            items(searchResults.playlists) {
                AM_MusicPlayerCompactListItemCard(
                    cardType = it.getAssociatedListCardType(),
                    thumbnailImageUrlString = it.imageUrlString ?: "",
                    title = it.name,
                    subtitle = "Playlist",
                    onClick = { onItemClick(it) },
                    onTrailingButtonIconClick = { onTrailingIconButtonClick(it) },
                    isLoadingPlaceHolderVisible = isLoadingPlaceHolderVisible(it),
                    onThumbnailImageLoadingFinished = { throwable ->
                        onImageLoadingFinished(it, throwable)
                    },
                    onThumbnailLoading = { onImageLoading(it) }
                )
            }
            item {
                Spacer(modifier = Modifier.navigationBarsHeight())
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(16.dp)
                .size(128.dp)
                .align(Alignment.Center)
                .offset(y = (-100).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .padding(16.dp),
            visible = isSearchResultLoadingAnimationVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LottieAnimation(
                composition = lottieComposition ,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}

private fun SearchResult.getAssociatedListCardType(): ListItemCardType = when (this) {
    is SearchResult.AlbumSearchResult -> ListItemCardType.ALBUM
    is SearchResult.ArtistSearchResult -> ListItemCardType.ARTIST
    is SearchResult.PlaylistSearchResult -> ListItemCardType.PLAYLIST
    is SearchResult.TrackSearchResult -> ListItemCardType.SONG
}