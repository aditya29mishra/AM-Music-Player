package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class AlbumsDTO(
    val items : List<AlbumMetadataDTO>,
    val limit : Int,
    @JsonProperty("next") val nextPageUrlString : String,
    val offset : Int,
    @JsonProperty ("previous") val previousPageUrlString : Any,
    @JsonProperty("total") val totalNumberOfItemsAvailable : Int
)
