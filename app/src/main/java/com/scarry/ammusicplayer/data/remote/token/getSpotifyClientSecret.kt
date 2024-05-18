package com.scarry.ammusicplayer.data.remote.token

import com.scarry.ammusicplayer.data.encoder.Base64Encoder
import com.scarry.ammusicplayer.BuildConfig
fun getSpotifyClientSecret(base64Encoder: Base64Encoder): String {
    val clientId = BuildConfig.SPOTIFY_CLIENT_ID
    val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET
    val encodedString =base64Encoder.encodeToString("$clientId:$clientSecret".toByteArray())
    return "Basic $encodedString"

}
