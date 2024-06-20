package com.kdani.network_helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdani.ktor.helpers.NetworkResponse as ktorNetwork
import com.kdani.network_helper.network.SampleKtorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val ktorService: SampleKtorService
) : ViewModel() {

    init {
        viewModelScope.launch {
            when (val res = ktorService.fetchData()) {
                is ktorNetwork.ApiError -> Timber.e("ktor error = ${res.throwable}")
                ktorNetwork.Empty -> Timber.e("ktor empty")
                is ktorNetwork.Success -> Timber.i("ktor sample res = $res")
            }
        }
    }
}