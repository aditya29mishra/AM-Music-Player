package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName
data class TracksWithAlbumMetadataListDTO(
    @SerializedName ("tracks") val tracks: List<TrackDTOWithAlbumMetadata>
)
