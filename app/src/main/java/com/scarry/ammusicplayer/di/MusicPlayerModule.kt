package com.scarry.ammusicplayer.di

import androidx.lifecycle.ViewModel
import com.scarry.ammusicplayer.musicPlayer.AmMusicBackgroundMusicPlayer
import com.scarry.ammusicplayer.musicPlayer.MusicPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
abstract class MusicPlayerModule {
    @Binds
    abstract fun bindMusicPlayer(
        amMusicBackgroundMusicPlayer : AmMusicBackgroundMusicPlayer
    ) : MusicPlayer
}