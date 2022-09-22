package com.hmdlong14.cryptocurrency.screens.coin.detail

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback.CoinResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback.HistoryResultCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.HistoryEntry
import com.hmdlong14.cryptocurrency.utils.base.BasePresenter

class CoinDetailPresenter(private val repository: CoinRepository)
    : BasePresenter<CoinDetailContract.View>, CoinDetailContract.Presenter {
    var detailCoin : Coin? = null
    private var _view : CoinDetailContract.View? = null

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }

    override fun setView(view: CoinDetailContract.View?) {
        _view = view
    }

    override fun getDetail(coin: Coin) {
        repository.getCoinDetail(coin.uuid, object : CoinResultCallback {
            override fun onSuccess(result: MutableList<Coin>) {
                detailCoin = result[0]
                _view?.onGetDetailSuccess(result[0])
            }

            override fun onFailed(exception: Exception) {
                _view?.onFail(exception)
            }
        })
        repository.getPriceHistory(coin, object : HistoryResultCallback {
            override fun onSuccess(result: List<HistoryEntry<Double>>) {
                _view?.onGetHistory(result)
            }

            override fun onFailed(exception: java.lang.Exception) {
                _view?.onFail(exception)
            }
        })
    }
}