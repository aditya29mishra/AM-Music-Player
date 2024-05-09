package com.scarry.ammusicplayer.Domain

import java.net.URL

sealed class MusicSummary (
    val id: String,
    val name: String,
    val associatedImageUrl: URL,
    val associationMetadata: String? = null,
    ){

    class TrackSummary(
           id: String,
           name: String,
           associatedImageUrl: URL,
           val nameOfArtist: String,
           val trackURL: URL,
           val numberOfPlays: Long,
           ): MusicSummary(id, name, associatedImageUrl, nameOfArtist)

       class AlbumSummary(
           id: String,
           name: String,
           val nameOfArtist: String,
           val albumArtURL: URL,
           val yearOfReleaseString : String,
           ): MusicSummary(id, name, albumArtURL, nameOfArtist)

       class ArtistSummary(
            id: String,
            name: String,
           associatedImageUrl: URL,
      ): MusicSummary(id, name,associatedImageUrl)

     class PlaylistSummary(
        id: String,
        name: String,
        associatedImageUrl: URL,
    ): MusicSummary(id, name, associatedImageUrl)

}