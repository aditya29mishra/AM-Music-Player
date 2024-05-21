package com.scarry.ammusicplayer.Domain

data class SearchResults(
    val tracks: List<MusicSummary.TrackSummary>,
    val albums: List<MusicSummary.AlbumSummary>,
    val artists: List<MusicSummary.ArtistSummary>,
    val playlists: List<MusicSummary.PlaylistSummary>
)
