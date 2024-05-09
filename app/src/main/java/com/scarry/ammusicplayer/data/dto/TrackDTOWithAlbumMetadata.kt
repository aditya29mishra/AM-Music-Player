package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class TrackDTOWithAlbumMetadata(
    val id: String,
    val name: String,
    @SerializedName("preview_url")val preview_url: String?,
    @SerializedName("is_playable")val is_playable: Boolean,
    val explicit: Boolean,
    @SerializedName("duration_ms")val duration_ms: Int,
    @SerializedName("album") val album: AlbumMetadataDTO
)
