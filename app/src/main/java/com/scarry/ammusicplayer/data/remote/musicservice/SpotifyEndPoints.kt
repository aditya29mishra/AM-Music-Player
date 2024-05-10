package com.scarry.ammusicplayer.data.remote.musicservice

object SpotifyEndPoints {
    const val SpecificArtistEndPoint = "v1/artists/{id}"
    const val SpecificArtistAlbumsEndPoint = "v1/artists/{id}/albums"
    const val SpecificAlbumEndPoint = "v1/albums/{id}"
    const val TopTracksEndPoint = "v1/artists/{id}/top-tracks"
    const val SpecificPlaylistEndPoint = "v1/playlists/{playlist_id}"
    const val SearchEndPoint = "v1/search"
    const val ApiTokenEndPoint = "api/token"

    object Defualt {
        const val defaultPlaylistFields = "id,image,name,description,owner.display_name,track.items,followers.total"
        val defaultSearchQueryTypes = buildSearchQueryWithTypes(*SearchQueryType.values())

    }
}
