# KtorStarter
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.kdani41/kdani-ktor-network)](https://central.sonatype.com/artifact/io.github.kdani41/kdani-ktor-network/)

### Description
Light weight library to simplify network calls using ktor & kotlinx serialization.

### Assumptions 
- kotlinx used for serialization.

### Features
- Allows easy access to build retrofit instance.
- Returns the responses in sealed wrapper called [`NetworkResponse`](https://github.com/kdani41/network-helper/blob/main/library/network/src/main/java/com/kdani/core/network/NetworkResponse.kt)
- Inbuilt no network connection manager.
- By default adds network permission for you.

### Installation 
```kotlin 

dependencies {
   implementation("io.github.kdani41:kdani-ktor-network:[version]") 
}

```

### [Usage](https://github.com/kdani41/network-helper/tree/main/app/src/main/java/com/kdani/network_helper)
TBD
