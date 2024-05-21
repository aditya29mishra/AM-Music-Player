package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.searchResult.AlbumSearchResult
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.data.utils.getImageDTOForImageSize
import java.net.URL

data class AlbumMetadataDTO(
    val id: String,
    val name: String,
    @JsonProperty("album_type") val album_type: String,
    val artists: List<ArtistInfoDTO>,
    val images: List<ImageDTO>,
    @JsonProperty("release_date") val release_date: String,
    @JsonProperty("release_date_precision") val release_date_precision: String?,
    @JsonProperty("total_tracks") val total_tracks: Int,
    val type: String
) {
    data class ArtistInfoDTO(
        val id: String ,
        val name: String
    )
}

fun AlbumMetadataDTO.toAlbumSummary(imageSize: MapperImageSize) = MusicSummary.AlbumSummary(
    id = id,
    name = name,
    nameOfArtist = artists.first().name,
    albumArtURL = URL (images.getImageDTOForImageSize(imageSize).url),
    yearOfReleaseString = release_date
)

fun AlbumMetadataDTO.toAlbumSearchResult(imageSize: MapperImageSize) = AlbumSearchResult(
    id = id,
    name = name,
    artistString = artists.joinToString (","),
    albumArtUrlString = images.getImageDTOForImageSize(imageSize).url,
    yearOfReleaseString = release_date
)
