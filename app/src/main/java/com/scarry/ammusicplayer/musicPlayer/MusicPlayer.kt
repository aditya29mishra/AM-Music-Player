package com.scarry.ammusicplayer.musicPlayer

interface MusicPlayer {
    fun playTrackFormUrlString(urlString: String)
    fun pauseCurrentlyPlayingTrack()
    fun stopPlayingTrack()
}