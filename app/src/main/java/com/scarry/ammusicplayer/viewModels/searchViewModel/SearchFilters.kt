package com.scarry.ammusicplayer.viewModels.searchViewModel

enum class SearchFilter (
    val filterLabel: String
){
    ALL("All"),
    ALBUMS("Albums"),
    TRACKS("Tracks"),
    ARTISTS("Artists"),
    PLAYLISTS("Playlists"),
}