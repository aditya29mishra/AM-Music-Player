package com.scarry.ammusicplayer.Domain

import java.net.URL

sealed class MusicSummary (
    open val id: String,
    open val name: String,
    val associationImageURL: URL,
    val associationMetadata: String? = null,
    ){
      data class TracKSummary(
          override val id: String,
          override val name: String,
          val nameOfArtist: String,
          val trackArtURL: URL,
          val trackURL: URL,
      ): MusicSummary(id, name, trackArtURL, nameOfArtist)

      data class AlbumSummary(
          override val id: String,
          override val name: String,
          val nameOfArtist: String,
          val albumArtURL: URL,
      ): MusicSummary(id, name, albumArtURL, nameOfArtist)

      data class ArtistSummary(
          override val id: String,
          override val name: String,
          val profilePictureUrl: URL,
      ): MusicSummary(id, name,profilePictureUrl)

    data class PlaylistSummary(
        override val id: String,
        override val name: String,
        val playlistArtURL: URL,
    ): MusicSummary(id, name, playlistArtURL)

}