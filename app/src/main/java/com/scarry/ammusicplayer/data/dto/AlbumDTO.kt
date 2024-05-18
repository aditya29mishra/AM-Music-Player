package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.data.utils.getImageDTOForImageSize
import java.net.URL

data class AlbumDTO(
    val id: String,
    val name: String,
    @JsonProperty("album_type") val albumType: String,
    val artists: List<ArtistDTOWithNullableImagesAndFollowers>,
    val images: List<ImageDTO>,
    @JsonProperty("release_date") val releaseDate: String,
    @JsonProperty("release_date_precision") val releaseDatePrecision: String,
    @JsonProperty("total_tracks") val totalTracks: Int,
    val tracks: TracksWithoutAlbumMetadataList
){
    data class TracksWithoutAlbumMetadataList(@JsonProperty("items") val value: List<TrackDTOWithoutAlbumMetadata>)
    data class TrackDTOWithoutAlbumMetadata(
        val id: String,
        val name: String,
        @JsonProperty("preview_url") val previewUrl: String?,
        @JsonProperty("is_playable") val isPlayable: Boolean,
        val explicit: Boolean,
        @JsonProperty("duration_ms") val durationInMillis: Int
    )

    data class ArtistDTOWithNullableImagesAndFollowers(
        val id: String,
        val name: String,
        val images: List<ImageDTO>?,
        val followers: ArtistDTO.Followers?
    )
}

fun AlbumDTO.toAlbumSummary(imageSize: MapperImageSize) =MusicSummary.AlbumSummary(
    id = id,
    name = name,
    nameOfArtist =artists.first().name,
    albumArtURL = URL( images.getImageDTOForImageSize(imageSize).url),
    yearOfReleaseString = releaseDate,
)