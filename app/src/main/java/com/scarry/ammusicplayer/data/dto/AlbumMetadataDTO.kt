package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class AlbumMetadataDTO(
    val id: Int,
    val name: String,
    @SerializedName("album_type")val album_type: String,
    val artists: List<ArtistInfoDTO>,
    val images: List<ImageDTO>,
    @SerializedName("release_date")val release_date: String,
    @SerializedName("release_date_precision") val release_date_precision: String,
    @SerializedName("total_tracks") val total_tracks: Int,
    val type: String
){
    data class ArtistInfoDTO(
        val id: Int,
        val name: String
    )
}
