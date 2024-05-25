package com.scarry.ammusicplayer.musicPlayer

import android.graphics.Bitmap

interface MusicPlayer {
    fun pauseCurrentlyPlayingTrack()
    fun stopPlayingTrack()
    fun playTrack(track: Track)
    data class Track(
        val id: String,
        val title: String,
        val artistsString: String,
        val albumArt: Bitmap,
        val trackUrlString:String
    )
}