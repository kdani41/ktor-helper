package com.kdani.ktor.network

/**
 * Interface for checking network connectivity across platforms
 */
interface NetworkConnectivityManager {
    /**
     * Check if network is currently available
     * @return true if network is available, false otherwise
     */
    suspend fun isNetworkAvailable(): Boolean
}

/**
 * Factory function to create platform-specific NetworkConnectivityManager
 * This will be implemented using expect/actual
 */
expect fun createNetworkConnectivityManager(): NetworkConnectivityManager