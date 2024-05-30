package com.scarry.ammusicplayer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.scarry.ammusicplayer.Domain.AM_Music_HttpErrorType
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.Domain.AM_MusicHttpErrorType
import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.toSupportedSpotifyGenreType
import com.scarry.ammusicplayer.data.dto.toAlbumSummary
import com.scarry.ammusicplayer.data.dto.toAlbumSummaryList
import com.scarry.ammusicplayer.data.dto.toArtistSummary
import com.scarry.ammusicplayer.data.dto.toPlaylistSummary
import com.scarry.ammusicplayer.data.dto.toSearchResults
import com.scarry.ammusicplayer.data.dto.toTrackSearchResult
import com.scarry.ammusicplayer.data.dto.toTrackSummary
import com.scarry.ammusicplayer.data.paging.SpotifyAlbumSearchPagingSource
import com.scarry.ammusicplayer.data.paging.SpotifyPagingSource
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyService
import com.scarry.ammusicplayer.data.remote.musicservice.SupportedSpotifyGenres
import com.scarry.ammusicplayer.data.remote.musicservice.toGenre
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import com.scarry.ammusicplayer.data.repository.tokenrepository.TokenRepository
import com.scarry.ammusicplayer.data.utils.FetchedResource
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class AM_MusicRepository @Inject constructor (
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
    ): FetchedResource<List<SearchResult.TrackSearchResult>, AM_Music_HttpErrorType> = withToken{
        spotifyService.getTopTenTracksForArtistWithId(
            artistId = artistId,
            market = countryCode,
            token = it,
        ).value.map { trackDTOWithAlbumMetadata ->
            trackDTOWithAlbumMetadata.toTrackSearchResult(imageSize)
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
    ): FetchedResource<SearchResults, AM_Music_HttpErrorType> = withToken{
        spotifyService.search(searchQuery,countryCode,it).toSearchResults(imageSize)
    }

    override  fun fetchAvailableGenre(): List<Genre> = SupportedSpotifyGenres.values().map{
        it.toGenre()
    }

    override suspend fun fetchTracksForGenre(
        genre: Genre,
        imageSize: MapperImageSize,
        countryCode: String
    ): FetchedResource<List<SearchResult.TrackSearchResult>, AM_Music_HttpErrorType> = withToken{
        spotifyService.getTracksForGenre(
            genre = genre.genreType.toSupportedSpotifyGenreType(),
            market = countryCode,
            token = it
        ).value.map { trackDTOWithAlbumMetadata ->
            trackDTOWithAlbumMetadata.toTrackSearchResult(imageSize)
        }
    }

    override fun getPaginatedSearchStreamForType(
        paginatedStreamType: Repository.PaginatedStreamType,
        searchQuery: String,
        countryCode: String,
        imageSize: MapperImageSize
    ): Flow<PagingData<out SearchResult>> {
        val pagingSource = when(paginatedStreamType) {
            Repository.PaginatedStreamType.ALBUM -> SpotifyAlbumSearchPagingSource(
                searchQuery = searchQuery,
                countryCode = countryCode,
                imageSize = imageSize,
                tokenRepository = tokenRepository,
                spotifyService = spotifyService
            )
            Repository.PaginatedStreamType.ARTIST -> SpotifyAlbumSearchPagingSource(
                searchQuery = searchQuery,
                countryCode = countryCode,
                imageSize = imageSize,
                tokenRepository = tokenRepository,
                spotifyService = spotifyService
            )
            Repository.PaginatedStreamType.TRACK -> SpotifyAlbumSearchPagingSource(
                searchQuery = searchQuery,
                countryCode = countryCode,
                imageSize = imageSize,
                tokenRepository = tokenRepository,
                spotifyService = spotifyService
            )
            Repository.PaginatedStreamType.PLAYLIST -> SpotifyAlbumSearchPagingSource(
                searchQuery = searchQuery,
                countryCode = countryCode,
                imageSize = imageSize,
                tokenRepository = tokenRepository,
                spotifyService = spotifyService
            )
        }
        return Pager(PagingConfig(SpotifyPagingSource.DEFAULT_PAGE_SIZE)) {pagingSource} .flow
    }

}