package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class AlbumDTO(
    val id: String,
    val name: String,
    @SerializedName("albumType") val albumType: String, // album,single or compilation
    val artists: List<ArtistDTO>,
    val images: List<ImageDTO>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("release_date_precision") val releaseDatePrecision: String, // year, month or day
    @SerializedName("total_tracks") val totalTracks: Int,
    val tracks: TracksWithoutAlbumMetadataList
){
    data class TracksWithoutAlbumMetadataList(@SerializedName("items") val value: List<TrackDTOWithoutAlbumMetadata>)
    data class TrackDTOWithoutAlbumMetadata(
        val id: String,
        val name: String,
        @SerializedName("preview_url") val previewUrl: String?,
        @SerializedName("is_playable") val isPlayable: Boolean,
        val explicit: Boolean,
        @SerializedName("duration_ms") val durationInMillis: Int
    )
}
