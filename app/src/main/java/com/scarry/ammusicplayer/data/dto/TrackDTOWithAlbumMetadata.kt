package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.scarry.ammusicplayer.Domain.MusicSummary
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.data.utils.MapperImageSize
import com.scarry.ammusicplayer.data.utils.getImageDTOForImageSize
import java.net.URL

data class TrackDTOWithAlbumMetadata(
    val id: String,
    val name: String,
    @JsonProperty("preview_url")val preview_url: String?,
    @JsonProperty("is_playable")val is_playable: Boolean    ,
    val explicit: Boolean,
    @JsonProperty("duration_ms")val duration_ms: Int,
    @JsonProperty("album") val albumMetadata: AlbumMetadataDTO
)

fun TrackDTOWithAlbumMetadata.toTrackSummary(imageSize: MapperImageSize)= MusicSummary.TrackSummary(
    id = id,
    name = name,
    associatedImageUrl =  URL(albumMetadata.images.getImageDTOForImageSize(imageSize).url),
    albumName = albumMetadata.name,
    trackURL =preview_url?.let (::URL)
)

fun TrackDTOWithAlbumMetadata.toTrackSearchResult(imageSize: MapperImageSize)=
    SearchResult.TrackSearchResult(
        id = id,
        name = name,
        imageUrlString = albumMetadata.images.getImageDTOForImageSize(imageSize).url,
        artistsString = albumMetadata.artists.joinToString(",") { it.name },
        trackUrlString = preview_url
    )
