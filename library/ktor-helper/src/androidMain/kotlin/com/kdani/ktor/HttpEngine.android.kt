package com.kdani.ktor

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

/**
 * Provides the Android HTTP engine (OkHttp-based)
 */
internal actual fun getHttpEngine(): HttpClientEngine = Android.create()
