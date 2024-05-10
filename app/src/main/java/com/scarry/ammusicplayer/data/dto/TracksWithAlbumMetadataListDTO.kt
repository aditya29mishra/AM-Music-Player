package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
data class TracksWithAlbumMetadataListDTO(
    @JsonProperty("tracks") val tracks: List<TrackDTOWithAlbumMetadata>
)
