package com.scarry.ammusicplayer.di

import com.scarry.ammusicplayer.data.encoder.AndroidBase64Encoder
import com.scarry.ammusicplayer.data.encoder.Base64Encoder
import com.scarry.ammusicplayer.data.repository.AM_MusicRepository
import com.scarry.ammusicplayer.data.repository.tokenrepository.SpotifyTokenRepository
import com.scarry.ammusicplayer.data.repository.tokenrepository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun bindBase64Encoder(
        androidBase64Encoder: AndroidBase64Encoder
    ): Base64Encoder

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        spotifyTokenRepository: SpotifyTokenRepository
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindRepository(
        am_misic_Repository: AM_MusicRepository
    ): AM_MusicRepository
}