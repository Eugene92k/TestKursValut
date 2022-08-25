package com.example.testkursvalute.api


sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()

    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success: $data"
            is Error -> "Error: $message"
            is Loading -> "Loading in progress"
        }
    }
}

val Result<*>.successed get() = this is Result.Success && data != null