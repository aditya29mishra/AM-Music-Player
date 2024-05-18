package com.scarry.ammusicplayer.data.utils

import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

/**
 * A sealed class hierarchy that is used to encapsulate a fetched
 * resource of type [ResourceType]. If any error occurs, the
 * [FailureType] will be used.
 */
sealed class FetchedResource<ResourceType , FailureType> {
    data class Success<ResourceType , FailureType> (
         val data: ResourceType
    ): FetchedResource<ResourceType , FailureType>()

    data class Failure<ResourceType , FailureType> (
         val cause: FailureType,
         val data: ResourceType? = null
    ): FetchedResource<ResourceType , FailureType>()

}