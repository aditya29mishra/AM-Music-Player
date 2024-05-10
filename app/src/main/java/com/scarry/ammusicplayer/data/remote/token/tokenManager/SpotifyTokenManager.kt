package com.scarry.ammusicplayer.data.remote.token.tokenManager

import com.scarry.ammusicplayer.data.remote.musicservice.SpotifyEndPoints
import com.scarry.ammusicplayer.data.remote.token.AccessTokenResponseDTO
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SpotifyTokenManager : TokenManager<Response<AccessTokenResponseDTO>> {

    @FormUrlEncoded
    @POST(SpotifyEndPoints.ApiTokenEndPoint)
    override suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Header("Authorization") secret: String
    ): Response<AccessTokenResponseDTO>
}