package com.hmdlong14.cryptocurrency.data.repository.sources

import com.hmdlong14.cryptocurrency.data.model.Coin
import java.lang.Exception

interface CoinRepoCallback {
    fun onSuccess(coins: MutableList<Coin>)
    fun onFailed(exception: Exception)
}
