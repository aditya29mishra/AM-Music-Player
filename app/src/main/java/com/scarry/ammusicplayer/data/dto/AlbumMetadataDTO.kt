package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class AlbumMetadataDTO(
    val id: Int,
    val name: String,
    @JsonProperty("album_type")val album_type: String,
    val artists: List<ArtistInfoDTO>,
    val images: List<ImageDTO>,
    @JsonProperty("release_date")val release_date: String,
    @JsonProperty("release_date_precision") val release_date_precision: String?,
    @JsonProperty("total_tracks") val total_tracks: Int,
    val type: String
){
    data class ArtistInfoDTO(
        val id: Int,
        val name: String
    )
}
