package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.scarry.ammusicplayer.Domain.MusicSummary
import java.net.URL

data class PlaylistDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    @JsonProperty("owner") val ownerName: OwnerNameWrapper,
    @JsonProperty("followers") val numberOfFollowers: NumberOfFollowersWrapper,
    val tracks: Tracks
) {
    data class OwnerNameWrapper(@JsonProperty("display_name") val value: String)
    data class NumberOfFollowersWrapper(@JsonProperty("total") val value: String)
    data class Tracks(val items: List<TrackDTOWithAlbumMetadataWrapper>)
    data class TrackDTOWithAlbumMetadataWrapper(@JsonProperty("track") val track: TrackDTOWithAlbumMetadata)
}

fun PlaylistDTO.toPlaylistSummary() = MusicSummary.PlaylistSummary(
    id = id,
    name = name,
    associatedImageUrl = URL(images.first().url)
)
