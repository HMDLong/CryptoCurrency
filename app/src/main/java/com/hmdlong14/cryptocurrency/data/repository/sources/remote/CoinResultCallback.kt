package com.hmdlong14.cryptocurrency.data.repository.sources.remote

import com.hmdlong14.cryptocurrency.data.model.Coin
import java.lang.Exception

interface CoinResultCallback {
    fun onSuccess(coins: MutableList<Coin>)
    fun onFailed(exception: Exception)
}
