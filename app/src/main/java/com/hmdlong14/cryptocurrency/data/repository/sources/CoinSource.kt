package com.hmdlong14.cryptocurrency.data.repository.sources

import android.content.Context
import com.hmdlong14.cryptocurrency.data.repository.StateKey
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.CoinResultCallback

interface CoinSource {
    interface Local {
        fun getFavoriteCoins(context : Context, callback: LocalCoinCallback)
        fun getHoldingCoins(context: Context, callback: LocalCoinCallback)
    }

    interface Remote {
        fun getCoinsData(state : Map<StateKey, Any>, callback: CoinResultCallback)
        fun getMoreCoinsData(state : Map<StateKey, Any>, callback: CoinResultCallback)
        fun getCoinDetail(uuid : String, callback: CoinResultCallback)
        fun searchCoins(state : Map<StateKey, Any>, callback: CoinResultCallback)
        fun getLocalCoinsData(coinsId : List<String>, callback: CoinResultCallback)
    }
}
