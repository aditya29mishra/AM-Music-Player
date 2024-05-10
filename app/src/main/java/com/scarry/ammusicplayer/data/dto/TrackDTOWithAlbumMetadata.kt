package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class TrackDTOWithAlbumMetadata(
    val id: String,
    val name: String,
    @JsonProperty("preview_url")val preview_url: String?,
    @JsonProperty("is_playable")val is_playable: Boolean,
    val explicit: Boolean,
    @JsonProperty("duration_ms")val duration_ms: Int,
    @JsonProperty("album") val album: AlbumMetadataDTO
)
