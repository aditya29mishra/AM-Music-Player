package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.scarry.ammusicplayer.data.utils.MapperImageSize

data class AlbumsMetadataDTO(
    val items: List<AlbumMetadataDTO>,
    val limit: Int,
    @JsonProperty("next") val nextPageUrlString: String,
    val offset: Int,
    @JsonProperty("previous") val previousPageUrlString: String?,
    @JsonProperty("total") val totalNumberOfItemsAvailable: Int
)
fun AlbumsMetadataDTO.toAlbumSummaryList(imageSize: MapperImageSize) = items.map {
    it.toAlbumSummary(imageSize)
}
