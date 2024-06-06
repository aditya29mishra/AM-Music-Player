package com.scarry.ammusicplayer.viewModels.searchViewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.Domain.emptySearchResults
import com.scarry.ammusicplayer.data.repository.Repository
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.di.AM_MusicApplication
import com.scarry.ammusicplayer.musicPlayer.MusicPlayer
import com.scarry.ammusicplayer.useCase.playTrackUseCase.AmMusicPlayTrackWithMediaNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchScreenUiState { LOADING, SUCCESS, IDLE }

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val repository: Repository,
    private val musicPlayer: MusicPlayer,
    private val playTrackWithMediaNotificationUseCase: AmMusicPlayTrackWithMediaNotificationUseCase
) : AndroidViewModel(application) {
    private var searchJob: Job? = null
    private val emptySearchResults = emptySearchResults()
    private val _uiState = mutableStateOf(SearchScreenUiState.IDLE)
    private val _searchResults = mutableStateOf(emptySearchResults)
    private val filteredSearchResult = mutableStateOf(emptySearchResults)
    val searchResults = _searchResults as State<SearchResults>
    @Deprecated("Use separate paginated item flows")
    val searchResult = filteredSearchResult as State<SearchResults>
    val uiState = _uiState as State<SearchScreenUiState>

    private val _albumListForSearchQuery = MutableStateFlow<PagingData<SearchResult.AlbumSearchResult>>(PagingData.empty())
    val albumListForSearchQuery = _albumListForSearchQuery as Flow<PagingData<SearchResult.AlbumSearchResult>>

    private val _artistListForSearchQuery = MutableStateFlow<PagingData<SearchResult.ArtistSearchResult>>(PagingData.empty())
    val artistListForSearchQuery = _artistListForSearchQuery as Flow<PagingData<SearchResult.ArtistSearchResult>>

    private val _trackListForSearchQuery = MutableStateFlow<PagingData<SearchResult.TrackSearchResult>>(PagingData.empty())
    val trackListForSearchQuery = _trackListForSearchQuery as Flow<PagingData<SearchResult.TrackSearchResult>>

    private val _playlistListForSearchQuery = MutableStateFlow<PagingData<SearchResult.PlaylistSearchResult>>(PagingData.empty())
    val playlistListForSearchQuery = _playlistListForSearchQuery as Flow<PagingData<SearchResult.PlaylistSearchResult>>

    private fun getCountryCode(): String =
        getApplication<AM_MusicApplication>().resources.configuration.locale.country

    private fun getSearchResultObjectForFilter(
        searchFilter: SearchFilter
    ) = if (searchFilter != SearchFilter.ALL) {
        SearchResults(
            tracks = if (searchFilter == SearchFilter.TRACKS) _searchResults.value.tracks
            else emptyList(),
            albums = if (searchFilter == SearchFilter.ALBUMS) _searchResults.value.albums
            else emptyList(),
            artists = if (searchFilter == SearchFilter.ARTISTS) _searchResults.value.artists
            else emptyList(),
            playlists = if (searchFilter == SearchFilter.PLAYLISTS) _searchResults.value.playlists
            else emptyList()
        )
    } else _searchResults.value

    private  suspend fun collectAndAssignSearchResults(
        searchQuery: String,
        imageSize: MapperImageSize
    ){
      repository.getPaginatedSearchStreamForType(
          paginatedStreamType = Repository.PaginatedStreamType.ALBUM,
          searchQuery = searchQuery,
          countryCode = getCountryCode(),
          imageSize = imageSize
      ).collect{
          _albumListForSearchQuery.value = it as PagingData<SearchResult.AlbumSearchResult>
      }
        repository.getPaginatedSearchStreamForType(
            paginatedStreamType = Repository.PaginatedStreamType.ALBUM,
            searchQuery = searchQuery,
            countryCode = getCountryCode(),
            imageSize = imageSize
        ).collect{
            _artistListForSearchQuery.value = it as PagingData<SearchResult.ArtistSearchResult>
        }

        repository.getPaginatedSearchStreamForType(
            paginatedStreamType = Repository.PaginatedStreamType.ALBUM,
            searchQuery = searchQuery,
            countryCode = getCountryCode(),
            imageSize = imageSize
        ).collect{
            _trackListForSearchQuery.value = it as PagingData<SearchResult.TrackSearchResult>
        }
        repository.getPaginatedSearchStreamForType(
            paginatedStreamType = Repository.PaginatedStreamType.ALBUM,
            searchQuery = searchQuery,
            countryCode = getCountryCode(),
            imageSize = imageSize
        ).collect{
            _playlistListForSearchQuery.value = it as PagingData<SearchResult.PlaylistSearchResult>
        }
    }


    fun searchWithFilter(
        searchQuery: String,
        searchFilter: SearchFilter = SearchFilter.ALL
    ) {
        searchJob?.cancel()
        if (searchQuery.isBlank()) {
            _searchResults.value = emptySearchResults
            filteredSearchResult.value = _searchResults.value
            _uiState.value = SearchScreenUiState.IDLE
            return
        }
        _uiState.value = SearchScreenUiState.LOADING
        searchJob = viewModelScope.launch {
            // add artificial delay to limit the number of calls to
            // the api when the user is typing the search query.
            // adding this delay allows for a short window of time
            // which could be used to cancel this coroutine if the
            // search text is currently being typed; preventing
            // un-necessary calls to the api
            delay(500)
            val searchResult = repository.fetchSearchResultForQuery(
                searchQuery = searchQuery.trim(),
                imageSize = MapperImageSize.MEDIUM,
                countryCode = getCountryCode()
            )
            collectAndAssignSearchResults(searchQuery, MapperImageSize.MEDIUM)
            if (searchResult is FetchedResource.Success) {
                _searchResults.value = searchResult.data
                filteredSearchResult.value = getSearchResultObjectForFilter(searchFilter)
            }
            _uiState.value = SearchScreenUiState.SUCCESS
        }
    }

    fun applyFilterToSearchResult(searchFilter: SearchFilter) {
        filteredSearchResult.value = getSearchResultObjectForFilter(searchFilter)
    }

    fun playTrack(track: SearchResult.TrackSearchResult) {
        if (track.trackUrlString == null) return
        viewModelScope.launch {
            _uiState.value = SearchScreenUiState.LOADING
            playTrackWithMediaNotificationUseCase.invoke(
                track,
                onLoading = {_uiState.value = SearchScreenUiState.LOADING},
                onFinishedLoading = { _uiState.value = SearchScreenUiState.IDLE}
            )
        }
    }

    fun getAvailableGenre() = repository.fetchAvailableGenre()
}
