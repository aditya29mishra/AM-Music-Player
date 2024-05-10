package com.scarry.ammusicplayer.data.remote.token

data class BearerToken (
    private val tokenString: String,
    val secondsUntilExpiration : Int
){
    val value get() = "Bearer $tokenString"
    override fun toString(): String = "Bearer $tokenString"
}