package com.scarry.ammusicplayer.data.remote.musicservice

import com.scarry.ammusicplayer.data.dto.AlbumDTO
import com.scarry.ammusicplayer.data.dto.AlbumMetadataDTO
import com.scarry.ammusicplayer.data.dto.ArtistDTO
import com.scarry.ammusicplayer.data.dto.PlaylistDTO
import com.scarry.ammusicplayer.data.dto.SearchResultsDTO
import com.scarry.ammusicplayer.data.dto.TracksWithAlbumMetadataListDTO
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {
    @GET(SpotifyEndPoints.SPECIFIC_ARTIST_ENDPOINT)

    suspend fun getArtistInfoWithId(
        @Path("id") artistId: String,
        @Header("Authorization") token: BearerToken,

        ): Response<ArtistDTO>

    @GET(SpotifyEndPoints.SPECIFIC_ARTIST_ALBUMS_ENDPOINT)
    suspend fun getAlbumsOfArtistWithId(
        @Path("id") artistId: String,
        @Header("Authorization") token: BearerToken,

        ): Response<List<AlbumMetadataDTO>>

    @GET(SpotifyEndPoints.SPECIFIC_ALBUM_ENDPOINT)
    suspend fun getAlbumInfoWithId(
        @Path("id") albumId: String,
        @Header("Authorization") token: BearerToken,

    ): Response<AlbumDTO>

    @GET(SpotifyEndPoints.SPECIFIC_PLAYLIST_ENDPOINT)
    suspend fun getPlaylistWithId(
        @Path("playlist_id") playlistId: String,
        @Header("Authorization") token: BearerToken,
        ): Response<List<PlaylistDTO>>

    @GET(SpotifyEndPoints.TOP_TRACKS_ENDPOINT)
    suspend fun getTopTracksOfArtistWithId(
        @Path("id") artistId: String,
        @Header("Authorization") token: BearerToken,
        ): Response<List<TracksWithAlbumMetadataListDTO>>

    @GET(SpotifyEndPoints.SEARCH_ENDPOINT)
    suspend fun search(
        @Query("q") searchQuery: String,
        @Header("Authorization") token: BearerToken,
        ): Response<SearchResultsDTO>



}