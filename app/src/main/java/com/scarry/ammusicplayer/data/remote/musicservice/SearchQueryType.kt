package com.scarry.ammusicplayer.data.remote.musicservice

enum class SearchQueryType (val value: String){
    ALBUM("album"),
    PLAYLIST("playlist"),
    TRACK("track"),
    ARTIST("artist")
}

fun buildSearchQueryWithTypes(vararg type: SearchQueryType) : String{
    if (type.isEmpty()) throw IllegalArgumentException("List cant be empty")
    var query = type.first().value
    for (i in 1..type.lastIndex){
        query += ",${type[i].value}"
    }
    return query
}


