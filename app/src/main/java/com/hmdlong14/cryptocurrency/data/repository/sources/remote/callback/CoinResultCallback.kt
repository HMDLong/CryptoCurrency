package com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback

import com.hmdlong14.cryptocurrency.data.model.Coin

interface CoinResultCallback : ResultCallback<MutableList<Coin>>
//{
//    fun onSuccess(coins: MutableList<Coin>)
//    fun onFailed(exception: Exception)
//}
