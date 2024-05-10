package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumDTO(
    val id: String,
    val name: String,
    @JsonProperty("album_type") val albumType: String, // album,single or compilation
    val artists: List<ArtistDTOWithNullableImagesAndFollowers>,
    val images: List<ImageDTO>,
    @JsonProperty("release_date") val releaseDate: String,
    @JsonProperty("release_date_precision") val releaseDatePrecision: String, // year, month or day
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
