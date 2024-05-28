package com.scarry.ammusicplayer.data.paging

import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.data.dto.toSearchResults
import com.scarry.ammusicplayer.data.remote.musicservice.SearchQueryType
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyService
import com.scarry.ammusicplayer.data.repository.tokenrepository.TokenRepository
import com.scarry.ammusicplayer.data.utils.MapperImageSize

@Suppress("FunctionName")
fun SpotifyTrackSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    imageSize: MapperImageSize,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.TrackSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.TRACK,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults(imageSize).tracks }
)

@Suppress("FunctionName")
fun SpotifyAlbumSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    imageSize: MapperImageSize,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.AlbumSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.ALBUM,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults(imageSize).albums }
)

@Suppress("FunctionName")
fun SpotifyArtistSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    imageSize: MapperImageSize,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.ArtistSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.ARTIST,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults(imageSize).artists }
)

@Suppress("FunctionName")
fun SpotifyPlaylistSearchPagingSource(
    searchQuery: String,
    countryCode: String,
    imageSize: MapperImageSize,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService
): SpotifySearchPagingSource<SearchResult.PlaylistSearchResult> = SpotifySearchPagingSource(
    searchQuery = searchQuery,
    countryCode = countryCode,
    searchQueryType = SearchQueryType.PLAYLIST,
    tokenRepository = tokenRepository,
    spotifyService = spotifyService,
    resultsBlock = { it.toSearchResults(imageSize).playlists }
)