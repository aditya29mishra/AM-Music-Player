package com.scarry.ammusicplayer.useCase.playTrackUseCase

import com.scarry.ammusicplayer.Domain.SearchResult
/**
 * A use case that is responsible for playing an instance of
 * [SearchResult.TrackSearchResult] and displaying
 * a media notification.
 */
interface PlayTrackWithMediaNotificationUseCase {
    suspend fun invoke(
        track: SearchResult.TrackSearchResult,
        onLoading :() -> Unit,
        onFinishedLoading : (Throwable?) -> Unit,
    )
}