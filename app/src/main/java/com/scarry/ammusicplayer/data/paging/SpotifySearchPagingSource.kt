package com.scarry.ammusicplayer.data.paging

import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.data.dto.SearchResultsDTO
import com.scarry.ammusicplayer.data.remote.musicservice.SearchQueryType
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyService
import com.scarry.ammusicplayer.data.repository.tokenrepository.TokenRepository
import retrofit2.HttpException

class SpotifySearchPagingSource<T : SearchResult>(
    searchQuery: String,
    countryCode: String,
    searchQueryType: SearchQueryType,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService,
    resultsBlock: (SearchResultsDTO) -> List<T>
) : SpotifyPagingSource<T>(
    loadBlock = { limit: Int, offset: Int, prevKey: Int?, nextKey: Int? ->
        try {
            val searchResultsDTO = spotifyService.search(
                searchQuery = searchQuery,
                market = countryCode,
                token = tokenRepository.getValidBearerToken(),
                limit = limit,
                offset = offset,
                type = searchQueryType.value
            )
            val data = resultsBlock(searchResultsDTO)
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey,
                itemsAfter = data.size
            )
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }
    }
)