package com.scarry.ammusicplayer.data.remote.token

import com.fasterxml.jackson.annotation.JsonProperty

data class AccessTokenResponseDTO(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val secondsUntilExpiration: Int,
    @JsonProperty("token_type") val tokenType: String
)

fun AccessTokenResponseDTO.toBearerToken() = BearerToken(
    tokenString = accessToken,
    secondsUntilExpiration = secondsUntilExpiration
)