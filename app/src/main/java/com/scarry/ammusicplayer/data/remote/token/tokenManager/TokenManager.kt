package com.scarry.ammusicplayer.data.remote.token.tokenManager

const val DEFUALT_GRANT_TYPE = "Client_credentials"
interface TokenManager<TokenType> {
    suspend fun getAccessToken(
        grantType : String = DEFUALT_GRANT_TYPE,
        secret : String
        ) : TokenType
}