package com.scarry.ammusicplayer.useCase.playTrackUseCase

import com.scarry.ammusicplayer.Domain.SearchResult

interface PlayTrackWithMediaNotificationUseCase {
    suspend fun invoke(
        track: SearchResult.TrackSearchResult,
        onLoading :() -> Unit,
        onFinishedLoading : (Throwable?) -> Unit,
    )
}