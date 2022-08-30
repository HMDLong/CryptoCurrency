package com.hmdlong14.cryptocurrency.data.repository.sources.remote

import com.hmdlong14.cryptocurrency.data.repository.sources.CoinRepoCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.GetJsonFromApi

class RemoteCoinSource : CoinSource.Remote {
    private var currentOffset = 0

    private fun setOffset(offset: Int){
        currentOffset = offset
    }

    override fun getCoinsData(callback: CoinRepoCallback) {
        GetJsonFromApi().getCoinsFromApi(currentOffset, this::setOffset, callback)
    }

    override fun getCoinDetail(uuid : String, callback: CoinRepoCallback) {
        GetJsonFromApi().getCoinDetailFromApi(uuid, callback)
    }

    override fun searchCoins(queryText: String, callback: CoinRepoCallback) {
        GetJsonFromApi().searchCoins(queryText, callback)
    }
}
