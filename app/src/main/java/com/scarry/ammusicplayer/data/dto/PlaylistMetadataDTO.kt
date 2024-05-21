package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.searchResult.PlaylistSearchResult
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.data.utils.getImageDTOForImageSize
import java.net.URL

data class PlaylistMetadataDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    @JsonProperty("owner") val ownerName: PlaylistDTO.OwnerNameWrapper
)

fun PlaylistMetadataDTO.toPlaylistSummary() =MusicSummary.PlaylistSummary(
    id = id,
    name = name,
    associatedImageUrl = URL(images.first().url)
)

fun PlaylistMetadataDTO.toPlaylistSearchResult() = PlaylistSearchResult(
    id = id,
    name = name,
    imageUrlString = images.first().url
)