package com.scarry.ammusicplayer.data.remote.token.tokenManager

import android.util.Base64
import com.scarry.ammusicplayer.BuildConfig
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyEndPoints
import com.scarry.ammusicplayer.data.remote.token.AccessTokenResponseDTO
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

@Deprecated(
    message = " use SpotifyClientSecret(base64encoder: Base64Encoder) instead",
    replaceWith = ReplaceWith("getSpotifyClientSecret()")
)
val SPOTIFY_CLIENT_SECRET_BASE64 : String
    get() = Base64.encodeToString(
        "Basic ${BuildConfig.SPOTIFY_CLIENT_ID}:${BuildConfig.SPOTIFY_CLIENT_SECRET}".toByteArray(),
        Base64.NO_WRAP
    )

const val defaultGrantType = "client_credentials"

interface TokenManager{

    @FormUrlEncoded
    @POST(SpotifyEndPoints.API_TOKEN_ENDPOINT)
     suspend fun getNewAccessToken(
        @Header("Authorization") secret: String,
        @Field("grant_type") grantType: String = defaultGrantType,
    ): AccessTokenResponseDTO
}