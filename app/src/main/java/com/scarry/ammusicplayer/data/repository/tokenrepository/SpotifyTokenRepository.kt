package com.scarry.ammusicplayer.data.repository.tokenrepository

import android.os.Build
import androidx.annotation.RequiresApi
import com.scarry.ammusicplayer.data.encoder.Base64Encoder
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import com.scarry.ammusicplayer.data.remote.token.getSpotifyClientSecret
import com.scarry.ammusicplayer.data.remote.token.isExpired
import com.scarry.ammusicplayer.data.remote.token.toBearerToken
import com.scarry.ammusicplayer.data.remote.token.tokenManager.TokenManager
import java.time.LocalDateTime
import javax.crypto.SecretKey
import javax.inject.Inject


class SpotifyTokenRepository @Inject constructor(
    private val tokenManager : TokenManager,
    private val base64Encoder: Base64Encoder
) : TokenRepository{
    private var token : BearerToken? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getValidBearerToken(): BearerToken {
        if (token == null || token?.isExpired == true) getAndAssignToken()
        return token!!

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getAndAssignToken( ) {
        val clientSecret = getSpotifyClientSecret(base64Encoder)
        token = tokenManager
            .getNewAccessToken(clientSecret)
            .toBearerToken()
    }

}