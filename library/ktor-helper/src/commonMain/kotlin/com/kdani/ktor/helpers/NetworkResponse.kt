package com.kdani.ktor.helpers

/**
 * Sealed class representing the result of a network request
 */
sealed class NetworkResponse<out T> {
    /**
     * Successful response with data
     */
    data class Success<T>(val data: T) : NetworkResponse<T>()

    /**
     * HTTP error response
     */
    data class Error(
        val message: String,
        val code: Int? = null,
        val throwable: Throwable? = null
    ) : NetworkResponse<Nothing>()

    /**
     * Network connectivity error
     */
    data class NetworkError(
        val exception: Throwable,
        val message: String = exception.message ?: "Network error occurred"
    ) : NetworkResponse<Nothing>()
}

/**
 * Extension function to map success data
 */
inline fun <T, R> NetworkResponse<T>.map(transform: (T) -> R): NetworkResponse<R> {
    return when (this) {
        is NetworkResponse.Success -> NetworkResponse.Success(transform(data))
        is NetworkResponse.Error -> this
        is NetworkResponse.NetworkError -> this
    }
}

/**
 * Extension function to handle success case
 */
inline fun <T> NetworkResponse<T>.onSuccess(action: (T) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.Success) {
        action(data)
    }
    return this
}

/**
 * Extension function to handle error case
 */
inline fun <T> NetworkResponse<T>.onError(action: (String, Int?) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.Error) {
        action(message, code)
    }
    return this
}

/**
 * Extension function to handle network error case
 */
inline fun <T> NetworkResponse<T>.onNetworkError(action: (Throwable) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.NetworkError) {
        action(exception)
    }
    return this
}