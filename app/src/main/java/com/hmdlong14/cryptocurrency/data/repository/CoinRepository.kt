package com.hmdlong14.cryptocurrency.data.repository

import com.hmdlong14.cryptocurrency.data.repository.sources.CoinRepoCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource

class CoinRepository private constructor(
    private val local : CoinSource.Local,
    private val remote : CoinSource.Remote
) : CoinSource.Remote, CoinSource.Local {

    override fun getLocalCoinData(callback: CoinRepoCallback) {
        //TODO implement later
    }

    override fun getCoinsData(callback : CoinRepoCallback) {
        remote.getCoinsData(callback)
    }

    override fun getCoinDetail(uuid : String, callback: CoinRepoCallback) {
        remote.getCoinDetail(uuid, callback)
    }

    override fun searchCoins(queryText: String, callback: CoinRepoCallback) {
        remote.searchCoins(queryText, callback)
    }

    companion object {
        private var instance: CoinRepository? = null
        fun getInstance(local : CoinSource.Local, remote: CoinSource.Remote) =
            instance ?: CoinRepository(local, remote).also { instance = it }
    }
}
