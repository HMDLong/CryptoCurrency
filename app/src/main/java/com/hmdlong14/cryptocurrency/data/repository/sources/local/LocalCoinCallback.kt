package com.hmdlong14.cryptocurrency.data.repository.sources.local

import java.lang.Exception

interface LocalCoinCallback {
    fun onSuccess(coinsId: List<String>)
    fun onFailed(exception : Exception)
}