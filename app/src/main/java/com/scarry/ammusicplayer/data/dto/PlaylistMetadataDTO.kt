package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class PlaylistMetadataDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    @SerializedName("owner") val ownerName: PlaylistDTO.OwnerNameWrapper
)
