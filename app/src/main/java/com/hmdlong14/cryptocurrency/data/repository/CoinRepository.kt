package com.hmdlong14.cryptocurrency.data.repository

import android.content.Context
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.CoinResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinCallback

enum class StateKey {
    QUERY_TEXT,
    CURR_CATEGORY
}

class CoinRepository private constructor(
    private val local : CoinSource.Local,
    private val remote : CoinSource.Remote
) : CoinSource.Remote, CoinSource.Local {

    /**
     * Local source
     */
    override fun getFavoriteCoins(context: Context, callback: LocalCoinCallback) {
        local.getFavoriteCoins(context, callback)
    }

    override fun getHoldingCoins(context: Context, callback: LocalCoinCallback) {
        local.getHoldingCoins(context, callback)
    }

    /**
     * Remote source
     */
    override fun getCoinsData(state: Map<StateKey, Any>, callback : CoinResultCallback) {
        remote.getCoinsData(state, callback)
    }

    override fun getMoreCoinsData(state: Map<StateKey, Any>, callback: CoinResultCallback) {
        remote.getMoreCoinsData(state, callback)
    }

    override fun getCoinDetail(uuid : String, callback: CoinResultCallback) {
        remote.getCoinDetail(uuid, callback)
    }

    override fun searchCoins(state: Map<StateKey, Any>, callback: CoinResultCallback) {
        remote.searchCoins(state, callback)
    }

    override fun getLocalCoinsData(coinsId: List<String>, callback: CoinResultCallback) {
        remote.getLocalCoinsData(coinsId, callback)
    }

    companion object {
        private var instance: CoinRepository? = null
        fun getInstance(local : CoinSource.Local, remote: CoinSource.Remote) =
            instance ?: CoinRepository(local, remote).also { instance = it }
    }
}
