package com.hmdlong14.cryptocurrency.screens.coin.detail

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.HistoryEntry

interface CoinDetailContract {
    interface View {
        fun onGetDetailSuccess(coin: Coin)
        fun onGetHistory(history: List<HistoryEntry<Double>>)
        fun onFail(exception: Exception)
    }

    interface Presenter {
        fun getDetail(coin: Coin)
    }
}