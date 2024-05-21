package com.scarry.ammusicplayer.Domain

data class SearchResults(
    val tracks: List<SearchResult.TrackSearchResult>,
    val albums: List<SearchResult.AlbumSearchResult>,
    val artists: List<SearchResult.ArtistSearchResult>,
    val playlists: List<SearchResult.PlaylistSearchResult>
)
fun emptySearchResults() = SearchResults(
    tracks = emptyList(),
    albums = emptyList(),
    artists = emptyList(),
    playlists = emptyList()
)
