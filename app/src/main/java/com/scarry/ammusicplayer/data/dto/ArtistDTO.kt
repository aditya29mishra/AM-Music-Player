package com.scarry.ammusicplayer.data.dto

import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.data.utils.getImageDTOForImageSize
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
    associatedImageUrl = URL(images.getImageDTOForImageSize(imageSize).url)
)
fun ArtistDTO.toArtistSearchResult(imageSize: MapperImageSize) = SearchResult.ArtistSearchResult(
    id = id,
    name = name,
    imageUrlString = images.getImageDTOForImageSize(imageSize).url
)

