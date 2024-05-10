package com.scarry.ammusicplayer.data.repository.tokenrepository

import com.scarry.ammusicplayer.data.remote.token.BearerToken

interface TokenRepository {
    suspend fun getBearerToken(): BearerToken
}