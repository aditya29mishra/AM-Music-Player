package com.scarry.ammusicplayer.Domain

import retrofit2.HttpException


enum class AM_Music_HttpErrorType {
    BAD_OR_EXPIRED_TOKEN,
    BAD_AUTH_REQUEST,
    RATE_LIMIT_EXCEEDED,
    INVALID_REQUEST,
    UNKNOWN_ERROR
}

val HttpException.musifyHttpErrorType: AM_Music_HttpErrorType
    get() =
        when (this.code()) {
            400 -> AM_Music_HttpErrorType.INVALID_REQUEST
            401 -> AM_Music_HttpErrorType.BAD_OR_EXPIRED_TOKEN
            403 -> AM_Music_HttpErrorType.BAD_AUTH_REQUEST
            429 -> AM_Music_HttpErrorType.RATE_LIMIT_EXCEEDED
            else -> AM_Music_HttpErrorType.UNKNOWN_ERROR
        }