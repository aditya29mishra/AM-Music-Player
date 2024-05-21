package com.scarry.ammusicplayer.viewModels.searchViewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.Domain.emptySearchResults
import com.scarry.ammusicplayer.data.repository.AM_MusicRepository
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.di.AM_MusicApplication
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
    private val repository: AM_MusicRepository,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : AndroidViewModel(application) {
    private var searchJob: Job? = null
    private val emptySearchResults = emptySearchResults()
    private val _uiState = mutableStateOf(SearchScreenUiState.IDLE)
    private val _searchResults = mutableStateOf(emptySearchResults)
    val searchResults = _searchResults as State<SearchResults>
    val uiState = _uiState as State<SearchScreenUiState>

    fun search(searchQuery: String) {
        _uiState.value = SearchScreenUiState.LOADING
        searchJob?.cancel()
        if (searchQuery.isBlank()) {
            _searchResults.value = emptySearchResults
            _uiState.value = SearchScreenUiState.SUCCESS
            return
        }
        val countryCode = getApplication<AM_MusicApplication>().resources.configuration.locale.country
        searchJob = viewModelScope.launch(defaultDispatcher) {
            delay(1_500)
            val searchResult = repository.fetchSearchResultForQuery(
                searchQuery  = searchQuery.trim(),
                imageSize = MapperImageSize.SMALL,
                countryCode = countryCode
            )
            if(searchResult is FetchedResource.Success) _searchResults.value = searchResult.data
            _uiState.value = SearchScreenUiState.SUCCESS
        }
    }
}