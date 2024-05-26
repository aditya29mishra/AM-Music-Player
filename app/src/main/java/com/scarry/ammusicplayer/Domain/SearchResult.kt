package com.scarry.ammusicplayer.Domain

import android.graphics.Bitmap
import com.scarry.ammusicplayer.musicPlayer.MusicPlayer

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

fun SearchResult.TrackSearchResult.toMusicPlayerTrack(albumArtBitmap  : Bitmap): MusicPlayer.Track{
    if (trackUrlString == null ) throw  IllegalStateException("the trackUrlString cant be null during conversation ")
    return MusicPlayer.Track(
        id = id,
        title = name,
        artistsString = artistsString,
        trackUrlString = trackUrlString,
        albumArt = albumArtBitmap

    )
}