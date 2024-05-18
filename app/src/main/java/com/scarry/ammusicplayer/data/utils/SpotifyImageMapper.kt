package com.scarry.ammusicplayer.data.utils

import com.scarry.ammusicplayer.data.dto.ImageDTO

fun List<ImageDTO>.getImageDTOForImageSize(imageSize: MapperImageSize): ImageDTO{
    if(this.size < 3 ) throw IllegalStateException("this list must have at least 3 elements")
    return when(imageSize){
        MapperImageSize.SMALL -> this[0]
        MapperImageSize.MEDIUM -> this[1]
        MapperImageSize.LARGE -> this[2]
    }
}