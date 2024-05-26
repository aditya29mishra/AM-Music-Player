package com.scarry.ammusicplayer.useCase.playTrackUseCase

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import com.scarry.ammusicplayer.Domain.SearchResult
import com.scarry.ammusicplayer.Domain.toMusicPlayerTrack
import com.scarry.ammusicplayer.di.IoDispatcher
import com.scarry.ammusicplayer.di.MainDispatcher
import com.scarry.ammusicplayer.musicPlayer.AmMusicBackgroundMusicPlayer
import com.scarry.ammusicplayer.musicPlayer.MusicPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AmMusicPlayTrackWithMediaNotificationUseCase @Inject constructor(
    @ApplicationContext private  val context: Context,
    private val musicPlayer: MusicPlayer,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): PlayTrackWithMediaNotificationUseCase{
    private suspend fun downloadBitmapFromUrl(urlString : String) : ImageResult {
        val imageRequest = ImageRequest.Builder(context)
            .data(urlString)
            .build()
        return ImageLoader(context).execute(imageRequest)
    }
    override suspend fun invoke(
        track: SearchResult.TrackSearchResult,
        onLoading: () -> Unit,
        onFinishedLoading: (Throwable?) -> Unit
    ) {
        onLoading()
        val downloadAlbumArtResult = withContext(ioDispatcher){
            downloadBitmapFromUrl(track.imageUrlString)
        }
        withContext(mainDispatcher){
            if (downloadAlbumArtResult is SuccessResult){
                musicPlayer. playTrack(track.toMusicPlayerTrack(downloadAlbumArtResult.drawable.toBitmap()))
                onFinishedLoading(null)
            }else{
                onFinishedLoading((downloadAlbumArtResult as ErrorResult).throwable)
            }
        }
    }

}