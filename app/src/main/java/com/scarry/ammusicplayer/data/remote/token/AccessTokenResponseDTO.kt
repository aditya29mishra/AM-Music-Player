package com.scarry.ammusicplayer.data.remote.token

import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class AccessTokenResponseDTO(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val secondsUntilExpiration: Int,
    @JsonProperty("token_type") val tokenType: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun AccessTokenResponseDTO.toBearerToken() = BearerToken(
    tokenString = accessToken,
    timeOfCreation = LocalDateTime.now(),
    secondsUntilExpiration = secondsUntilExpiration
)