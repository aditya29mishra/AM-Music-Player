package com.scarry.ammusicplayer.data.remote.musicservice

import com.scarry.ammusicplayer.Domain.Genre
import com.scarry.ammusicplayer.data.dto.AlbumDTO
import com.scarry.ammusicplayer.data.dto.AlbumsMetadataDTO
import com.scarry.ammusicplayer.data.dto.ArtistDTO
import com.scarry.ammusicplayer.data.dto.PlaylistDTO
import com.scarry.ammusicplayer.data.dto.SearchResultsDTO
import com.scarry.ammusicplayer.data.dto.TracksWithAlbumMetadataListDTO
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

enum class SupportedSpotifyGenres (private  val queryStringValue: String){
    AMBIENT("ambient"),
    CHILL("chill"),
    CLASSICAL("classical"),
    DANCE("dance"),
    ELECTRICAL("electrical"),
    METAL("metal"),
    RAINY_DAY("rainy-day"),
    ROCK("rock"),
    PIANO("piano"),
    POP("pop")
}

fun SupportedSpotifyGenres.toGenre():  Genre{
    val genreType = getGenreType()
    val name = when(genreType){
        Genre.GenreType.AMBIENT -> "ambient"
        Genre.GenreType.CHILL -> "chill"
        Genre.GenreType.CLASSICAL -> "classical"
        Genre.GenreType.DANCE -> "dance"
        Genre.GenreType.ELECTRONIC -> "electronic"
        Genre.GenreType.METAL -> "metal"
        Genre.GenreType.RAINY_DAY -> "rainy-day"
        Genre.GenreType.ROCK -> "rock"
        Genre.GenreType.PIANO -> "piano"
        Genre.GenreType.POP -> "pop"
    }
    return Genre(
        id = "${this.name} ${ordinal}",
        name = name,
        genreType = genreType
    )
}

private fun SupportedSpotifyGenres.getGenreType() = when(this) {
    SupportedSpotifyGenres.AMBIENT -> Genre.GenreType.AMBIENT
    SupportedSpotifyGenres.CHILL -> Genre.GenreType.CHILL
    SupportedSpotifyGenres.CLASSICAL -> Genre.GenreType.CLASSICAL
    SupportedSpotifyGenres.DANCE -> Genre.GenreType.DANCE
    SupportedSpotifyGenres.ELECTRICAL -> Genre.GenreType.ELECTRONIC
    SupportedSpotifyGenres.METAL -> Genre.GenreType.METAL
    SupportedSpotifyGenres.RAINY_DAY -> Genre.GenreType.RAINY_DAY
    SupportedSpotifyGenres.ROCK -> Genre.GenreType.ROCK
    SupportedSpotifyGenres.PIANO -> Genre.GenreType.PIANO
    SupportedSpotifyGenres.POP ->Genre.GenreType.POP
}

interface SpotifyService {
    @GET(SpotifyEndPoints.SPECIFIC_ARTIST_ENDPOINT)
    suspend fun getArtistInfoWithId(
        @Path("id") artistId: String,
        @Header("Authorization") token: BearerToken,
    ): ArtistDTO

    @GET(SpotifyEndPoints.SPECIFIC_ARTIST_ALBUMS_ENDPOINT)
    suspend fun getAlbumsOfArtistWithId(
        @Path("id") artistId: String,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("include_groups") includeGroups: String? = null,
    ): AlbumsMetadataDTO

    @GET(SpotifyEndPoints.TOP_TRACKS_ENDPOINT)
    suspend fun getTopTenTracksForArtistWithId(
        @Path("id") artistId: String,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken
    ): TracksWithAlbumMetadataListDTO

    @GET(SpotifyEndPoints.SPECIFIC_ALBUM_ENDPOINT)
    suspend fun getAlbumWithId(
        @Path("id") albumId: String,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken
    ): AlbumDTO

    @GET(SpotifyEndPoints.SPECIFIC_PLAYLIST_ENDPOINT)
    suspend fun getPlaylistWithId(
        @Path("playlist_id") playlistId: String,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken,
        @Query("fields") fields: String = SpotifyEndPoints.Defaults.defaultPlaylistFields
    ): PlaylistDTO

    @GET(SpotifyEndPoints.SEARCH_ENDPOINT)
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("type") type: String = SpotifyEndPoints.Defaults.defaultSearchQueryTypes,
    ): SearchResultsDTO
    @GET(SpotifyEndPoints.RECOMMENDATIONS_ENDPOINT)
    suspend fun getTracksForGenre(
        @Query("seed_genres") genre: SupportedSpotifyGenres,
        @Query("market") market: String,
        @Header("Authorization") token: BearerToken,
        @Query("limit") limit: Int = 20
    ): TracksWithAlbumMetadataListDTO
}