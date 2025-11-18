# Ktor Helper — Kotlin Multiplatform Networking Library
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.kdani41/kdani-ktor-network)](https://central.sonatype.com/artifact/io.github.kdani41/kdani-ktor-network/)

A lightweight **Kotlin Multiplatform (KMP)** wrapper around **Ktor Client**, designed to provide a clean, unified, and testable networking layer for Android, iOS, and JVM.

This library simplifies:

- Client creation via `buildClient()`
- JSON serialization
- Base URL handling
- Timeout configuration
- Offline detection (via `NetworkInterceptor`)
- Custom Ktor configuration via `HttpClientInterceptor` (λ-based)

---

# ✨ Features

- 🧩 Simple, extensible **`buildClient()`** API
- 🌍 KMP-ready (`commonMain`)
- 🔐 Auth + header injection via **lambda interceptors**
- 📡 Automatic base URL injection
- ⏳ Unified timeout handling
- 📶 Automatic network availability detection
- 🧪 Testable, modular design

---

# 📦 Installation

```kotlin
commonMain {
    dependencies {
        implementation("com.kdani.ktor.helper:ktor-helper")
    }
}
```

---

# 🚀 Creating the Client

### Example

```kotlin
class ApiClient {

    private val client = KtorHelper.buildClient(
        baseUrl = "https://api.example.com",
        timeoutInMillis = 10_000,
        interceptors = listOf(
            authInterceptor,
        )
    )
}
```

---

# 🛠 Custom Interceptor Examples

## 1. Auth Header Interceptor

```kotlin
val authInterceptor: HttpClientInterceptor = {
    defaultRequest {
        val token = tokenProvider()
        if (token != null) {
            headers.append("Authorization", "Bearer $token")
        }
    }
}
```

Usage:

```kotlin
buildClient(
    baseUrl = "https://api.example.com",
    interceptors = listOf(authInterceptor)
)
```

---

## 2. Logging Interceptor

```kotlin
val loggingInterceptor: HttpClientInterceptor = {
    install(Logging) {
        level = LogLevel.ALL
    }
}
```

---

# 📡 Making Requests


### GET

```kotlin
suspend fun fetchUser(id: String): NetworkResponse<UserDto> =
    client.safeGet<UserDto>("/users/$id")
```

---

### POST

```kotlin
suspend fun createUser(name: String): NetworkResponse<UserDto> =
    client.safePost<UserDto>(
        urlString = "/users",
        body = CreateUserRequest(name)
    )
```

---

# 🧱 Models Example

```kotlin
@kotlinx.serialization.Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
)
```

