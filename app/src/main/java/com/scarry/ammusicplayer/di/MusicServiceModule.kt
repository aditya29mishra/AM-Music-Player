package com.scarry.ammusicplayer.di

import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyService
import com.scarry.ammusicplayer.data.remote.token.tokenManager.TokenManager
import com.scarry.ammusicplayer.utils.defaultAM_MusicJacksonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object MusicServiceModule {
    @Provides
    @Singleton
    fun PorivdeSpotifyModule(): SpotifyService = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .addConverterFactory(defaultAM_MusicJacksonConverterFactory)
        .build()
        .create(SpotifyService::class.java)

    @Provides
    @Singleton
    fun ProvidesTokenManeger() : TokenManager = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(defaultAM_MusicJacksonConverterFactory)
        .build()
        .create(TokenManager::class.java)
}