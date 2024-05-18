package com.scarry.ammusicplayer.data.remote.musicservice

object SpotifyEndPoints {
    const val SPECIFIC_ARTIST_ENDPOINT = "v1/artists/{id}"
    const val SPECIFIC_ARTIST_ALBUMS_ENDPOINT = "v1/artists/{id}/albums"
    const val SPECIFIC_ALBUM_ENDPOINT = "v1/albums/{id}"
    const val TOP_TRACKS_ENDPOINT = "v1/artists/{id}/top-tracks"
    const val SPECIFIC_PLAYLIST_ENDPOINT = "v1/playlists/{playlist_id}"
    const val SEARCH_ENDPOINT = "v1/search"
    const val API_TOKEN_ENDPOINT = "api/token"


    object Defaults {
        const val defaultPlaylistFields = "id,image,name,description,owner.display_name,track.items,followers.total"
        val defaultSearchQueryTypes = buildSearchQueryWithTypes(*SearchQueryType.values())

    }
}
