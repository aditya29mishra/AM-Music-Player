package com.scarry.ammusicplayer.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.scarry.ammusicplayer.Domain.SearchResults
import com.scarry.ammusicplayer.data.utils.MapperImageSize

data class SearchResultsDTO(
    val tracks: Tracks?,
    val albums: Albums?,
    val artists: Artists?,
    val playlists: Playlists?
){
    data class Tracks(@JsonProperty("items") val value: List<TrackDTOWithAlbumMetadata>)
    data class Albums(@JsonProperty("items") val value: List<AlbumMetadataDTO>)
    data class Artists(@JsonProperty("items") val value: List<ArtistDTO>)
    data class Playlists(@JsonProperty("items") val value: List<PlaylistMetadataDTO>)
}

fun SearchResultsDTO.toSearchResults(imageSize: MapperImageSize) = SearchResults(
    tracks = tracks?.value?.map { it.toTrackSummary(imageSize) } ?:emptyList(),
    albums = albums?.value?.map { it.toAlbumSummary(imageSize) } ?:emptyList(),
    artists = artists?.value?.map { it.toArtistSummary(imageSize) } ?:emptyList(),
    playlists = playlists?.value?.map { it.toPlaylistSummary() } ?:emptyList()
)
