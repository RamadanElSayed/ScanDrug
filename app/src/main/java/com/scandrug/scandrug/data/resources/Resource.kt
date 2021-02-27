package com.scandrug.scandrug.data.resources
sealed class Resource<out T>(val data: T? = null,
                              val message: String? = null
) {
    class Success<T>(data:T) : Resource<T>(data)
    class Failure<T>(data:T) : Resource<T>( data)
    class Loading<T> (data: T?): Resource<T>()
}
