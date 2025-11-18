package com.kdani.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

/**
 * Provides the JavaScript HTTP engine (Fetch API-based)
 */
internal actual fun getHttpEngine(): HttpClientEngine = Js.create()
