package com.scarry.ammusicplayer.data.repository.tokenrepository

import android.os.Build
import androidx.annotation.RequiresApi
import com.scarry.ammusicplayer.data.remote.token.BearerToken
import com.scarry.ammusicplayer.data.remote.token.toBearerToken
import com.scarry.ammusicplayer.data.remote.token.tokenManager.SPOTIFY_CLIENT_SECRET_BASE64
import com.scarry.ammusicplayer.data.remote.token.tokenManager.TokenManager
import java.time.LocalDateTime
import javax.crypto.SecretKey

class SpotifyTokenRepository(
    private val tokenManager : TokenManager
) : TokenRepository{
    private var token : BearerToken? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getBearerToken(): BearerToken {
        if (token == null) getAndAssignToken()
        return token!!.apply {
            val timeOfExpiration = timeOfCreation.plusSeconds(secondsUntilExpiration.toLong())
            val tokenExpired = LocalDateTime.now() > timeOfExpiration
            if (tokenExpired) getAndAssignToken()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getAndAssignToken(clientSecret: String = SPOTIFY_CLIENT_SECRET_BASE64 ) {
        token = tokenManager
            .getNewAccessToken(clientSecret)
            .toBearerToken()
    }

}