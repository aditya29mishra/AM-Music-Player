package com.scarry.ammusicplayer.data.dto

import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import java.net.URL

data class ArtistDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    val followers: Followers
) {
    data class Followers(val total: String)
}

fun ArtistDTO.toArtistSummary(imageSize: MapperImageSize) = MusicSummary.ArtistSummary(
    id = id,
    name = name,
    associatedImageUrl = URL(
        when (imageSize) {
            MapperImageSize.SMALL -> images[2]
            MapperImageSize.MEDIUM -> images[1]
            MapperImageSize.LARGE -> images[0]
        }.url
    )
)

