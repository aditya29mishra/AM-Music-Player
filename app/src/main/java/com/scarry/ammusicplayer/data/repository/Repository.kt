package com.scarry.ammusicplayer.data.repository

import com.scarry.ammusicplayer.Domain.AM_Music_HttpErrorType
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize

interface Repository {
    suspend fun fetchArtisSummaryForId(
        artistId: String,
        imageSize: MapperImageSize
    ):FetchedResource<MusicSummary.ArtistSummary , AM_Music_HttpErrorType>

    suspend fun fetchAlbumOfArtistForId(
        albumId: String,
        imageSize: MapperImageSize,
        CountryCode:String
    ):FetchedResource<List<MusicSummary.AlbumSummary >, AM_Music_HttpErrorType>


    suspend fun fetchTopTenTracksForArtistWithId(
        trackId: String,
        imageSize: MapperImageSize,
        CountryCode:String
    ):FetchedResource<List<MusicSummary.TrackSummary> , AM_Music_HttpErrorType>

    suspend fun fetchAlbumWithId(
        albumId: String,
        imageSize: MapperImageSize,
        CountryCode:String
    ) : FetchedResource<MusicSummary.AlbumSummary , AM_Music_HttpErrorType>

    suspend fun fetchPlaylistWithId(
        playlistId: String,
        imageSize: MapperImageSize,
    ):FetchedResource<MusicSummary.PlaylistSummary , AM_Music_HttpErrorType>

    suspend fun fetchSearchResultForQuery(
        searchQuery: String,
        imageSize: MapperImageSize,
        CountryCode:String
    ) :FetchedResource<SearchResult , AM_Music_HttpErrorType>
}