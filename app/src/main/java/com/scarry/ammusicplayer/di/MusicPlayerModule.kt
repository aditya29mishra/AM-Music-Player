package com.scarry.ammusicplayer.di

import androidx.lifecycle.ViewModel
import com.scarry.ammusicplayer.musicPlayer.AmMusicBackgroundMusicPlayer
import com.scarry.ammusicplayer.musicPlayer.MusicPlayer
import com.scarry.ammusicplayer.useCase.playTrackUseCase.AmMusicPlayTrackWithMediaNotificationUseCase
import com.scarry.ammusicplayer.useCase.playTrackUseCase.PlayTrackWithMediaNotificationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
/**
 * Note: The dependencies are not scoped because the underlying
 * media player is always a singleton. [ExoPlayerModule.provideExoplayer]
 * is annotated with @Singleton, therefore any class that depends on it
 * need to be a singleton since the class will be provided the same
 * instance.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class MusicPlayerModule {
    @Binds
    abstract fun bindMusicPlayer(
        amMusicBackgroundMusicPlayer : AmMusicBackgroundMusicPlayer
    ) : MusicPlayer

    @Binds
    abstract fun bindPlayTrackWithMediaNotificationUseCase(
    impl: AmMusicPlayTrackWithMediaNotificationUseCase
    ):PlayTrackWithMediaNotificationUseCase
}