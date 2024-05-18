package com.scarry.ammusicplayer.data.repository

import com.scarry.ammusicplayer.Domain.AM_Music_HttpErrorType
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.AM_MusicHttpErrorType
import com.scarry.ammusicplayer.data.dto.toAlbumSummary
import com.scarry.ammusicplayer.data.dto.toAlbumSummaryList
import com.scarry.ammusicplayer.data.dto.toArtistSummary
import com.scarry.ammusicplayer.data.dto.toPlaylistSummary
import com.scarry.ammusicplayer.data.dto.toSearchResults
import com.scarry.ammusicplayer.data.dto.toTrackSummary
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyService
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import com.scarry.ammusicplayer.data.repository.tokenrepository.TokenRepository
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import retrofit2.HttpException

class AM_MusicRepository (
    private val spotifyService: SpotifyService,
    private val tokenRepository: TokenRepository
): Repository {
    private suspend fun<R> withToken(block: suspend (BearerToken) -> R) :FetchedResource<R, AM_Music_HttpErrorType> =
        try {
            FetchedResource.Success(block(tokenRepository.getValidBearerToken()))
        }
        catch (
            httpException : HttpException
        ){
            FetchedResource.Failure(httpException.AM_MusicHttpErrorType)
        }
    override suspend fun fetchArtisSummaryForId(
        artistId: String,
        imageSize: MapperImageSize
    ): FetchedResource<MusicSummary.ArtistSummary, AM_Music_HttpErrorType>  = withToken {
        spotifyService.getArtistInfoWithId(artistId, it).toArtistSummary(imageSize)
    }

    override suspend fun fetchAlbumsOfArtistWithId(
        albumId: String,
        imageSize: MapperImageSize,
        countryCode: String
    ): FetchedResource<List<MusicSummary.AlbumSummary>, AM_Music_HttpErrorType> = withToken {
        spotifyService.getAlbumsOfArtistWithId(
            albumId,
            countryCode,
            it
        ).toAlbumSummaryList(imageSize)
    }
    override suspend fun fetchTopTenTracksForArtistWithId(
        artistId: String,
        imageSize: MapperImageSize,
        countryCode: String
    ): FetchedResource<List<MusicSummary.TrackSummary>, AM_Music_HttpErrorType> = withToken{
        spotifyService.getTopTenTracksForArtistWithId(
            artistId = artistId,
            market = countryCode,
            token = it,
        ).value.map { trackDTOWithAlbumMetadata ->
            trackDTOWithAlbumMetadata.toTrackSummary(imageSize)
        }
    }

    override suspend fun fetchAlbumWithId(
        albumId: String,
        imageSize: MapperImageSize,
        countryCode: String
    ): FetchedResource<MusicSummary.AlbumSummary, AM_Music_HttpErrorType>  = withToken{
        spotifyService.getAlbumWithId(albumId,countryCode,it).toAlbumSummary(imageSize)
    }

    override suspend fun fetchPlaylistWithId(
        playlistId: String,
        countryCode: String
    ): FetchedResource<MusicSummary.PlaylistSummary, AM_Music_HttpErrorType> = withToken{
        spotifyService.getPlaylistWithId(playlistId , countryCode , it).toPlaylistSummary()
    }

    override suspend fun fetchSearchResultForQuery(
        searchQuery: String,
        imageSize: MapperImageSize,
        countryCode: String
    ): FetchedResource<SearchResult, AM_Music_HttpErrorType> = withToken{
        spotifyService.search(searchQuery,countryCode,it).toSearchResults(imageSize)
    }

}