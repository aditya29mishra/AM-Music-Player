package com.scarry.ammusicplayer.viewModels.searchViewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.Domain.emptySearchResults
import com.scarry.ammusicplayer.data.repository.AM_MusicRepository
import com.scarry.ammusicplayer.data.repository.Repository
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.di.AM_MusicApplication
import com.scarry.ammusicplayer.di.DefaultDispatcher
import com.scarry.ammusicplayer.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchScreenUiState{LOADING, SUCCESS, IDLE}
@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AndroidViewModel(application) {
    private var searchJob: Job? = null
    private val emptySearchResults = emptySearchResults()
    private val _uiState = mutableStateOf(SearchScreenUiState.IDLE)
    private val _searchResults = mutableStateOf(emptySearchResults)
    private  val filteredSearchResult = mutableStateOf(emptySearchResults)
    val searchResults = _searchResults as State<SearchResults>
    val uiState = _uiState as State<SearchScreenUiState>

    private  fun gerCountryCode() : String = getApplication<AM_MusicApplication>().resources.configuration.locale.country

    private  fun getSearchResultObjectForFilter(
        searchFilter: SearchFilter
    ) = if (searchFilter !=SearchFilter.ALL) {
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
    }else _searchResults.value

    fun searchWithFilter(
        searchQuery: String,
        searchFilter: SearchFilter = SearchFilter.ALL
    ){
        searchJob?.cancel()
        if (searchQuery.isBlank()) {
            _searchResults.value = emptySearchResults
            filteredSearchResult.value = _searchResults.value
            _uiState.value = SearchScreenUiState.IDLE
            return
        }
        _uiState.value = SearchScreenUiState.LOADING
        searchJob = viewModelScope.launch(ioDispatcher) {
            // add artificial delay to limit the number of calls to
            // the api when the user is typing the search query.
            // adding this delay allows for a short window of time
            // which could be used to cancel this coroutine if the
            // search text is currently being typed; preventing
            // un-necessary calls to the api
            delay(500)
            val searchResult = repository.fetchSearchResultForQuery(
                searchQuery  = searchQuery.trim(),
                imageSize = MapperImageSize.SMALL,
                countryCode = gerCountryCode()
            )
            if(searchResult is FetchedResource.Success) {
                _searchResults.value = searchResult.data
               filteredSearchResult.value = getSearchResultObjectForFilter(searchFilter)
            }
            _uiState.value = SearchScreenUiState.SUCCESS
        }
    }
    fun applyFilterToSearchResult(searchFilter: SearchFilter) {
        filteredSearchResult.value = getSearchResultObjectForFilter(searchFilter)
    }
}
