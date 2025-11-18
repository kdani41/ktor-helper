package com.kdani.network_helper.network

import com.kdani.ktor.helpers.NetworkResponse
import com.kdani.ktor.helpers.safeGet
import com.kdani.network_helper.network.ktor.KtorEmployees
import io.ktor.client.HttpClient
import javax.inject.Inject

internal interface SampleKtorService {
    suspend fun fetchData(): NetworkResponse<KtorEmployees>
}

internal class SampleKtorServiceImpl @Inject constructor(private val client: HttpClient) :
    SampleKtorService {
    override suspend fun fetchData(): NetworkResponse<KtorEmployees> =
        client.safeGet<KtorEmployees>(
            url = "employees.json",
        )
}