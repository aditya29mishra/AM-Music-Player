package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class PlaylistDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    @SerializedName("owner") val ownerName: OwnerNameWrapper,
    @SerializedName("followers") val numberOfFollowers: NumberOfFollowersWrapper,
    val tracks: Tracks
) {
    data class OwnerNameWrapper(@SerializedName("display_name") val value: String)
    data class NumberOfFollowersWrapper(@SerializedName("total") val value: String)
    data class Tracks(val items: List<TrackDTOWithAlbumMetadataWrapper>)
    data class TrackDTOWithAlbumMetadataWrapper(@SerializedName("track") val track: TrackDTOWithAlbumMetadata)
}
