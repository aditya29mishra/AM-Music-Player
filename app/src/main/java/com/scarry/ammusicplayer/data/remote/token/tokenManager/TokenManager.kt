package com.scarry.ammusicplayer.data.remote.token.tokenManager

import com.google.gson.internal.GsonBuildConfig
import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyEndPoints
import com.scarry.ammusicplayer.data.remote.token.AccessTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.Base64

val SPOTIFY_CLIENT_SECRET_BASE64 : String = ""
//    get() = Base64.encodeToString(
//        "Basic ${BuildConfig.SPOTIFY_CLIENT_ID}:${BuildConfig.SPOTIFY_CLIENT_SECRET}".toByteArray(),
//        Base64.NO_WRAP


const val defaultGrantType = "client_credentials"

interface TokenManager{

    @FormUrlEncoded
    @POST(SpotifyEndPoints.API_TOKEN_ENDPOINT)
     suspend fun getNewAccessToken(
        @Header("Authorization") secret: String,
        @Field("grant_type") grantType: String = defaultGrantType,
    ): AccessTokenResponseDTO
}