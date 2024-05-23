package com.scarry.ammusicplayer.musicPlayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AmMusicBackgroundMusicPlayer @Inject constructor(
    @ApplicationContext context: Context,
    private val exoPlayer: ExoPlayer
) : MusicPlayer {
    private val notificationManager =
        PlayerNotificationManager.Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
            .setChannelImportance(NotificationUtil.IMPORTANCE_LOW).build().apply {
            setPlayer(exoPlayer)
        }

    override fun playTrackFormUrlString(urlString: String) {
        with(exoPlayer) {
            if (isPlaying) exoPlayer.stop()

            setMediaItem(MediaItem.fromUri(urlString))
            prepare()
            play()
        }
    }

    override fun pauseCurrentlyPlayingTrack() {
        exoPlayer.pause()
    }

    override fun stopPlayingTrack() {
        exoPlayer.stop()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID =
            "com.scarry.ammusicplayer.musicPlayer.MusicPlayerService.NOTIFICATION_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "playback controls"
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL_DISCRIPTION =
            "Notifications that are used to control playback"

    }

}