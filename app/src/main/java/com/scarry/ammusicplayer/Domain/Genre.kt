package com.scarry.ammusicplayer.Domain

data class Genre(
    val id: String,
    val name: String,
){
    enum class GenreType {
        AMBIENT,
        CHILL,
        CLASSICAL,
        DANCE,
        ELECTRONIC,
        METAL,
        RAINY_DAY,
        ROCK,
        PIANO,
        POP
    }
}
