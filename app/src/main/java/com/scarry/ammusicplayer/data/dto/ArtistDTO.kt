package com.scarry.ammusicplayer.data.dto

data class ArtistDTO(
    val id: String,
    val name: String,
    val images: List<ImageDTO>,
    val followers: Followers
){
    data class Followers(val total: String)
}
