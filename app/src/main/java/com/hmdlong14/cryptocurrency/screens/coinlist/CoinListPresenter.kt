package com.hmdlong14.cryptocurrency.screens.coinlist

import android.content.Context
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.StateKey
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.CoinResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinCallback

class CoinListPresenter(private val repository: CoinRepository) : ViewCoinsContract.Presenter {

    private var mView : ViewCoinsContract.View? = null
    private var resultCallback : CoinResultCallback? = null

    fun setView(view: ViewCoinsContract.View){
        mView = view
        resultCallback = object : CoinResultCallback {
            override fun onSuccess(coins: MutableList<Coin>) {
                mView?.onGetCoinsSuccess(coins)
            }

            override fun onFailed(exception: Exception) {
                mView?.onGetCoinFailed(exception)
            }
        }
    }

    override fun getFavoriteCoins(context : Context){
        repository.getFavoriteCoins(context, object : LocalCoinCallback {
            override fun onSuccess(coinsId: List<String>) {
                resultCallback?.let { repository.getLocalCoinsData(coinsId, it) }
            }

            override fun onFailed(exception: Exception) {
                mView?.onGetCoinFailed(exception)
            }
        })
    }

    override fun searchCoins(state : Map<StateKey, Any>?) {
        resultCallback?.let { repository.searchCoins(state, it) }
    }

    override fun getCoins(state: Map<StateKey, Any>?) {
        resultCallback?.let { repository.getCoinsData(state, it) }
    }

    override fun getMoreCoins(state: Map<StateKey, Any>?) {
        resultCallback?.let { repository.getMoreCoinsData(state, it) }
    }
}