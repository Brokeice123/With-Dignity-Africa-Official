package com.example.withdignityfinal

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val exception: Exception, val data: T? = null) : Resource<T>()

    data object Unspecified : Resource<Nothing>()

}