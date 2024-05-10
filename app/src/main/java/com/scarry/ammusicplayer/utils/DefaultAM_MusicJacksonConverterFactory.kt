package com.scarry.ammusicplayer.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.converter.jackson.JacksonConverterFactory

val defaultAM_MusicJacksonConverterFactory : JacksonConverterFactory = JacksonConverterFactory.create(
    jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
)