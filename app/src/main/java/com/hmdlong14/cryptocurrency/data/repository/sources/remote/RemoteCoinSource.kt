package com.hmdlong14.cryptocurrency.data.repository.sources.remote

import com.hmdlong14.cryptocurrency.data.repository.StateKey
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.GetJsonFromApi

class RemoteCoinSource : CoinSource.Remote {
    private var currentOffset = 0

    private fun setOffset(offset: Int){
        currentOffset = offset
    }

    override fun getMoreCoinsData(state: Map<StateKey, Any>, callback: CoinResultCallback) {
        GetJsonFromApi().getMoreCoinsFromApi(state, currentOffset, this::setOffset, callback)
    }

    override fun getCoinsData(state: Map<StateKey, Any>, callback: CoinResultCallback){
        GetJsonFromApi().getCoinsFromApi(state, callback)
    }

    override fun getCoinDetail(uuid : String, callback: CoinResultCallback) {
        GetJsonFromApi().getCoinDetailFromApi(uuid, callback)
    }

    override fun searchCoins(state: Map<StateKey, Any>, callback: CoinResultCallback) {
        GetJsonFromApi().searchCoins(state, callback)
    }

    override fun getLocalCoinsData(coinsId: List<String>, callback: CoinResultCallback) {
        GetJsonFromApi().getCoinsById(coinsId, callback)
    }
}
