package com.scarry.ammusicplayer.Domain

sealed class SearchResult {
    data class AlbumSearchResult(
        val id: String,
        val name: String,
        val artistsString: String,
        val albumArtUrlString: String,
        val yearOfReleaseString: String,
    ) : SearchResult()
    data class ArtistSearchResult(
        val id: String,
        val name: String,
        val imageUrlString: String?
    ) : SearchResult()
    data class PlaylistSearchResult(
        val id: String,
        val name: String,
        val imageUrlString: String?
    ) : SearchResult()
    data class TrackSearchResult(
        val id: String,
        val name: String,
        val imageUrlString: String,
        val artistsString: String,
        val trackUrlString: String?
    ) : SearchResult()
}