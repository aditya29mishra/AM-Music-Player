package com.scarry.ammusicplayer.data.repository

import androidx.paging.PagingData
import com.scarry.ammusicplayer.Domain.AM_Music_HttpErrorType
import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import kotlinx.coroutines.flow.Flow

interface Repository {
    enum class PaginatedStreamType {ALBUM ,ARTIST, TRACK,PLAYLIST}
    suspend fun fetchArtisSummaryForId(
        artistId: String,
        imageSize: MapperImageSize
    ):FetchedResource<MusicSummary.ArtistSummary , AM_Music_HttpErrorType>

    suspend fun fetchAlbumsOfArtistWithId(
        albumId: String,
        imageSize: MapperImageSize,
        countryCode:String
    ):FetchedResource<List<MusicSummary.AlbumSummary >, AM_Music_HttpErrorType>


    suspend fun fetchTopTenTracksForArtistWithId(
        artistId: String,
        imageSize: MapperImageSize,
        countryCode:String
    ):FetchedResource<List<MusicSummary.TrackSummary> , AM_Music_HttpErrorType>

    suspend fun fetchAlbumWithId(
        albumId: String,
        imageSize: MapperImageSize,
        countryCode:String
    ) : FetchedResource<MusicSummary.AlbumSummary , AM_Music_HttpErrorType>

    suspend fun fetchPlaylistWithId(
        playlistId: String,
        countryCode: String,
    ):FetchedResource<MusicSummary.PlaylistSummary , AM_Music_HttpErrorType>

    suspend fun fetchSearchResultForQuery(
        searchQuery: String,
        imageSize: MapperImageSize,
        countryCode:String
    ) :FetchedResource<SearchResults , AM_Music_HttpErrorType>

    fun  fetchAvailableGenre() : List<Genre>

    suspend fun  fetchTracksForGenre(
        genre: Genre,
        imageSize: MapperImageSize,
        countryCode: String,
    ): FetchedResource<List<SearchResult.TrackSearchResult>,AM_Music_HttpErrorType>

    fun getPaginatedSearchStreamForType(
        paginatedStreamType: PaginatedStreamType,
        searchQuery: String,
        countryCode: String,
        imageSize: MapperImageSize
    ): Flow<PagingData<out SearchResult>>
}