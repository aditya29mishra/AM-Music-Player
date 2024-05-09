package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class AlbumsDTO(
    val items : List<AlbumMetadataDTO>,
    val limit : Int,
    @SerializedName("next") val nextPageUrlString : String,
    val offset : Int,
    @SerializedName ("previous") val previousPageUrlString : Any,
    @SerializedName ("total") val totalNumberOfItemsAvailable : Int
)
