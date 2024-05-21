package com.scarry.ammusicplayer.Domain

import com.scarry.ammusicplayer.Domain.searchResult.AlbumSearchResult
import com.scarry.ammusicplayer.Domain.searchResult.ArtistSearchResult
import com.scarry.ammusicplayer.Domain.searchResult.PlaylistSearchResult
import com.scarry.ammusicplayer.Domain.searchResult.TrackSearchResult

data class SearchResults(
    val tracks: List<TrackSearchResult>,
    val albums: List<AlbumSearchResult>,
    val artists: List<ArtistSearchResult>,
    val playlists: List<PlaylistSearchResult>
)
