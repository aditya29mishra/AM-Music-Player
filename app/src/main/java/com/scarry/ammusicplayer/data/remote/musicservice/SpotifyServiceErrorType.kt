package com.scarry.ammusicplayer.data.remote.musicservice

import retrofit2.HttpException

enum class SpotifyServiceErrorType {
    BAD_OR_EXPIRED_TOKEN,
    BAD_OAUTH_REQUEST,
    RATE_LIMIT_EXCEEDED,
    UNKNOWN_ERROR
}

val HttpException.spotifyApiErrorType: SpotifyServiceErrorType
    get() =
        when (this.code()) {
            401 -> SpotifyServiceErrorType.BAD_OR_EXPIRED_TOKEN
            403 -> SpotifyServiceErrorType.BAD_OAUTH_REQUEST
            429 -> SpotifyServiceErrorType.RATE_LIMIT_EXCEEDED
            else -> SpotifyServiceErrorType.UNKNOWN_ERROR
        }
