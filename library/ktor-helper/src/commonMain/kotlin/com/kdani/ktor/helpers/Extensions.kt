package com.kdani.ktor.helpers

import com.kdani.ktor.RequestOptions
import com.kdani.ktor.exceptions.NoNetworkException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Perform a GET request and return a wrapped NetworkResponse
 */
suspend inline fun <reified T> HttpClient.safeGet(
    url: String,
    requestOptions: RequestOptions? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): NetworkResponse<T> = performSafeRequest {
    val response = get(url) {
        requestOptions?.let { applyRequestOptions(it) }
        block()
    }
    response.body<T>()
}

/**
 * Perform a POST request and return a wrapped NetworkResponse
 */
suspend inline fun <reified T> HttpClient.safePost(
    url: String,
    body: Any,
    requestOptions: RequestOptions? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): NetworkResponse<T> = performSafeRequest {
    val response = post(url) {
        contentType(ContentType.Application.Json)
        setBody(body)
        requestOptions?.let { applyRequestOptions(it) }
        block()
    }
    response.body<T>()
}

/**
 * Perform a PUT request and return a wrapped NetworkResponse
 */
suspend inline fun <reified T> HttpClient.safePut(
    url: String,
    body: Any,
    requestOptions: RequestOptions? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): NetworkResponse<T> = performSafeRequest {
    val response = put(url) {
        contentType(ContentType.Application.Json)
        setBody(body)
        requestOptions?.let { applyRequestOptions(it) }
        block()
    }
    response.body<T>()
}

/**
 * Perform a DELETE request and return a wrapped NetworkResponse
 */
suspend inline fun <reified T> HttpClient.safeDelete(
    url: String,
    requestOptions: RequestOptions? = null,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): NetworkResponse<T> = performSafeRequest {
    val response = delete(url) {
        requestOptions?.let { applyRequestOptions(it) }
        block()
    }
    response.body<T>()
}

/**
 * Internal helper to execute requests safely and wrap responses
 */
@PublishedApi
internal suspend inline fun <T> performSafeRequest(
    crossinline block: suspend () -> T
): NetworkResponse<T> {
    return try {
        val data = block()
        NetworkResponse.Success(data)
    } catch (e: NoNetworkException) {
        NetworkResponse.NetworkError(
            exception = e,
            message = "No network connection available"
        )
    } catch (e: ClientRequestException) {
        NetworkResponse.Error(
            message = e.response.status.description,
            code = e.response.status.value,
            throwable = e
        )
    } catch (e: ServerResponseException) {
        NetworkResponse.Error(
            message = "Server error: ${e.response.status.description}",
            code = e.response.status.value,
            throwable = e
        )
    } catch (e: Exception) {
        NetworkResponse.NetworkError(
            exception = e,
            message = e.message ?: "Unknown network error"
        )
    }
}

/**
 * Apply request options to the request builder
 */
@PublishedApi
internal fun HttpRequestBuilder.applyRequestOptions(options: RequestOptions) {
    options.headers.forEach { (key, value) ->
        headers.append(key, value)
    }
    options.queryParams.forEach { (key, value) ->
        url.parameters.append(key, value)
    }
}