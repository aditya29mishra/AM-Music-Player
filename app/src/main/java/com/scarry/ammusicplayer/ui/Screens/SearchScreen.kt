package com.scarry.ammusicplayer.ui.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.getDefaultLazyLayoutKey
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.navigationBarsHeight
import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.R
import com.scarry.ammusicplayer.ui.Components.AM_MusicPlayerCompactListItemCard
import com.scarry.ammusicplayer.ui.Components.FilterChip
import com.scarry.ammusicplayer.ui.Components.GenreCard
import com.scarry.ammusicplayer.ui.Components.ListItemCardType
import com.scarry.ammusicplayer.viewModels.searchViewModel.SearchFilter
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    genreList: List<Genre>,
    searchScreenFilters: List<SearchFilter>,
    onSearchFilterClicked: (SearchFilter) -> Unit,
    onGenreItemClick: (Genre) -> Unit,
    onSearchTextChanged: (searchText: String, filter: SearchFilter) -> Unit,
    isSearchResultLoading: Boolean,
    searchQueryResult: SearchResults,
    onSearchQueryItemClicked: (SearchResult) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    val isGenreImageLoadingMap = remember { mutableStateMapOf<String, Boolean>() }
    var isSearchListVisible by remember { mutableStateOf(false) }
    val isClearSearchTextButtonVisible by remember { derivedStateOf { isSearchListVisible && searchText.isNotEmpty() } }
    val focusManager = LocalFocusManager.current
    var currentlySelectedSearchScreenFilter by remember { mutableStateOf(SearchFilter.TRACKS) }
    val textFieldTrailingButton = @Composable {
        AnimatedVisibility(
            visible = isClearSearchTextButtonVisible,
            enter = fadeIn() + slideInHorizontally { it },
            exit = slideOutHorizontally { it } + fadeOut()
        ) {
            IconButton(
                onClick = {
                    searchText = " "
                    onSearchTextChanged("", currentlySelectedSearchScreenFilter)
                },
                content = { Icon(imageVector = Icons.Filled.Close, contentDescription = null) }
            )
        }
    }
    val isSearchItemLoadingPlaceHolderVisibleMap =
        remember { mutableStateMapOf<SearchResult, Boolean>() }
    val isSearchResultLoadingAnimationComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_loading_anim)
    )
    val isFilterChipGroupVisible by remember { derivedStateOf { isSearchListVisible } }
    val horizontalPaddingModifier = Modifier.padding(horizontal = 16.dp)
    BackHandler(isSearchListVisible) {
        focusManager.clearFocus()
        if (searchText.isNotEmpty()){
            searchText = ""
            // notify the caller that the text has been emptied out
            onSearchTextChanged(searchText, currentlySelectedSearchScreenFilter)
        }
        isSearchListVisible = false
    }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val filterChipGroupScrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = horizontalPaddingModifier,
            text = "Search",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(horizontalPaddingModifier)
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
                onSearchTextChanged(it, currentlySelectedSearchScreenFilter)
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
        AnimatedVisibility(
            visible = isFilterChipGroupVisible
        ) {
            FilterChipGroup(

                scrollState = filterChipGroupScrollState,
                filters = SearchFilter.values().toList(),
                currentlySelectedFilter = currentlySelectedSearchScreenFilter,
                onFilterClicked = {
                    currentlySelectedSearchScreenFilter = it
                    onSearchFilterClicked(it)
                    coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                }
            )
        }
        Box(modifier = horizontalPaddingModifier) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(170.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(this.maxCurrentLineSpan) } ) {
                    Text(
                        text = "Genres",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                items(items = genreList) {
                    GenreCard(
                        genre = it,
                        modifier = Modifier.height(120.dp),
                        onClick = { onGenreItemClick(it) },
                        imageResourceId = getImageResourceForGenreType(it.genreType),
                        backgroundColor = getBackgroundColorForGenreType(it.genreType)
                    )
                }
                item(span = { GridItemSpan(this.maxCurrentLineSpan) }) {
                    Spacer(modifier = Modifier.navigationBarsHeight())
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
                    lottieComposition = isSearchResultLoadingAnimationComposition,
                    lazyListState = lazyListState
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
    lazyListState: LazyListState = rememberLazyListState()
) {
    val artistImageErrorPainter = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.baseline_account_circle_24))
    val playlistsImageErrorPainter = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.baseline_music_note_24))
   Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = lazyListState
        ) {
            items(searchResults.tracks, key = { it.id }) {
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
            items(searchResults.albums, key = { it.id }) {
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
            items(searchResults.artists, key = { it.id }) {
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
                    onThumbnailLoading = { onImageLoading(it) },
                    errorPainter = artistImageErrorPainter
                )
            }
            items(searchResults.playlists, key = { it.id }) {
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
                    onThumbnailLoading = { onImageLoading(it) },
                    errorPainter = playlistsImageErrorPainter
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
                composition = lottieComposition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}

@Composable
fun FilterChipGroup(
    scrollState: ScrollState,
    filters: List<SearchFilter>,
    currentlySelectedFilter: SearchFilter,
    onFilterClicked: (SearchFilter) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp)
) {
    val currentLayoutDirection = LocalLayoutDirection.current
    val startPadding = contentPadding.calculateStartPadding(currentLayoutDirection)
    val endPadding = contentPadding.calculateEndPadding(currentLayoutDirection)
    Row(
        modifier = Modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.width(startPadding))
        filters.forEach {
            FilterChip(
                text = it.filterLabel,
                isSelected = it == currentlySelectedFilter,
                onClick = { onFilterClicked(it) }
            )
        }
        Spacer(modifier = Modifier.width(endPadding))
    }
}

private fun SearchResult.getAssociatedListCardType(): ListItemCardType = when (this) {
    is SearchResult.AlbumSearchResult -> ListItemCardType.ALBUM
    is SearchResult.ArtistSearchResult -> ListItemCardType.ARTIST
    is SearchResult.PlaylistSearchResult -> ListItemCardType.PLAYLIST
    is SearchResult.TrackSearchResult -> ListItemCardType.TRACK
}
fun getImageResourceForGenreType(genre: Genre.GenreType) = when(genre) {
    Genre.GenreType.AMBIENT -> R.drawable.genre_img_ambient
    Genre.GenreType.CHILL -> R.drawable.genre_img_chill
    Genre.GenreType.CLASSICAL -> R.drawable.genre_img_classical
    Genre.GenreType.DANCE -> R.drawable.genre_img_dance
    Genre.GenreType.ELECTRONIC -> R.drawable.genre_img_electronic
    Genre.GenreType.METAL -> R.drawable.genre_img_metal
    Genre.GenreType.RAINY_DAY -> R.drawable.genre_img_rainy_day
    Genre.GenreType.ROCK -> R.drawable.genre_img_rock
    Genre.GenreType.PIANO -> R.drawable.genre_img_piano
    Genre.GenreType.POP -> R.drawable.genre_img_pop
    Genre.GenreType.SLEEP -> R.drawable.genre_img_sleep
}
private fun getBackgroundColorForGenreType(genreType: Genre.GenreType) = when (genreType) {
    Genre.GenreType.AMBIENT -> Color(0, 48, 72)
    Genre.GenreType.CHILL -> Color(71, 126, 149)
    Genre.GenreType.CLASSICAL -> Color(141, 103, 171)
    Genre.GenreType.DANCE -> Color(140, 25, 50)
    Genre.GenreType.ELECTRONIC -> Color(186, 93, 7)
    Genre.GenreType.METAL -> Color(119, 119, 119)
    Genre.GenreType.RAINY_DAY -> Color(144, 168, 192)
    Genre.GenreType.ROCK -> Color(230, 30, 50)
    Genre.GenreType.PIANO -> Color(71, 125, 149)
    Genre.GenreType.POP -> Color(141, 103, 171)
    Genre.GenreType.SLEEP ->Color(30,50,100)
}