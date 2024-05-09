package com.scarry.ammusicplayer.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResultsDTO(
    val tracks: Tracks,
    val albums: Albums,
    val artists: Artists,
    val playlists: Playlists
){
    data class Tracks(@SerializedName("items") val value: List<TrackDTOWithAlbumMetadata>)
    data class Albums(@SerializedName("items") val value: List<AlbumMetadataDTO>)
    data class Artists(@SerializedName("items") val value: List<ArtistDTO>)
    data class Playlists(@SerializedName("items") val value: List<PlaylistMetadataDTO>)
}
