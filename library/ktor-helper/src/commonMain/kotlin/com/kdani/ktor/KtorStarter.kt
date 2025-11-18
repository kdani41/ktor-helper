package com.kdani.ktor

import com.kdani.ktor.helpers.NetworkInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

typealias HttpClientInterceptor = (HttpClientConfig<*>.() -> Unit)

/**
 * Main entry point for creating configured Ktor HttpClient instances
 * Automatically selects the appropriate HTTP engine for each platform
 */
object KtorStarter {

    /**
     * Build a configured HttpClient with automatic network checking
     *
     * @param baseUrl Base URL for all requests
     * @param timeoutInMillis Request timeout in milliseconds
     * @param interceptors Additional client configuration interceptors
     * @return Configured HttpClient instance
     */
    fun buildClient(
        baseUrl: String,
        timeoutInMillis: Long = 5000L,
        interceptors: List<HttpClientInterceptor> = emptyList()
    ): HttpClient {
        require(baseUrl.isNotEmpty()) {
            "base url=$baseUrl shouldn't be empty"
        }

        return HttpClient(getHttpEngine()) {
            // JSON content negotiation
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            // Default request configuration
            defaultRequest {
                url.takeFrom(baseUrl)
            }

            // Timeout configuration
            install(HttpTimeout) {
                requestTimeoutMillis = timeoutInMillis
                connectTimeoutMillis = timeoutInMillis
                socketTimeoutMillis = timeoutInMillis
            }

            // Network connectivity check before each request
            install(NetworkInterceptor)

            // Apply custom interceptors
            interceptors.forEach(::apply)
        }
    }
}

/**
 * Platform-specific HTTP engine
 * This will be provided by each platform using expect/actual
 */
internal expect fun getHttpEngine(): io.ktor.client.engine.HttpClientEngine