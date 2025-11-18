package com.kdani.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

/**
 * Provides the Darwin (iOS/macOS) HTTP engine (NSURLSession-based)
 */
internal actual fun getHttpEngine(): HttpClientEngine = Darwin.create()
