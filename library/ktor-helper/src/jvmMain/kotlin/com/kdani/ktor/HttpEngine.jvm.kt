package com.kdani.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

/**
 * Provides the CIO (Coroutine I/O) HTTP engine for JVM
 */
internal actual fun getHttpEngine(): HttpClientEngine = CIO.create()
