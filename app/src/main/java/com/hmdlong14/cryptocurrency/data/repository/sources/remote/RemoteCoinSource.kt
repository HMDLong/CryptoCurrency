package com.hmdlong14.cryptocurrency.data.repository.sources.remote

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.StateKey
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback.CoinResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback.HistoryResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.GetJsonFromApi

class RemoteCoinSource : CoinSource.Remote {
    private var currentOffset = 0
    private var cache = mutableListOf<Coin>()

    private fun setOffset(offset: Int){
        currentOffset = offset
    }

    private fun addToCache(data: List<Coin>){
        cache.addAll(data)
    }

    private fun setToCache(data: List<Coin>){
        cache.apply {
            clear()
            addAll(data)
        }
    }

    override fun getMoreCoinsData(state: Map<StateKey, Any>?, callback: CoinResultCallback) {
        GetJsonFromApi().getMoreCoinsFromApi(state, currentOffset, this::setOffset, callback, this::addToCache)
    }

    override fun getCoinsData(state: Map<StateKey, Any>?, callback: CoinResultCallback){
        GetJsonFromApi().getCoinsFromApi(state, callback, this::setToCache)
    }

    override fun getCoinDetail(uuid : String, callback: CoinResultCallback) {
        GetJsonFromApi().getCoinDetailFromApi(uuid, callback)
    }

    override fun searchCoins(state: Map<StateKey, Any>?, callback: CoinResultCallback) {
        GetJsonFromApi().searchCoins(state, callback)
    }

    override fun getLocalCoinsData(coinsId: List<String>, callback: CoinResultCallback) {
        GetJsonFromApi().getCoinsById(coinsId, callback)
    }

    override fun getCacheCoin(callback: CoinResultCallback) {
        if(cache.isEmpty()){
            callback.onFailed(Exception("Empty cache"))
        } else {
            callback.onSuccess(cache)
        }
    }

    override fun getPriceHistory(coin: Coin, callback: HistoryResultCallback) {
        GetJsonFromApi().getCoinHistory(coin.uuid, callback)
    }
}
