package com.hmdlong14.cryptocurrency.screens.coin.detail

import com.hmdlong14.cryptocurrency.data.model.Coin
import java.lang.Exception

interface CoinDetailContract {
    interface View {
        fun onGetDetailSuccess(coin: Coin)
        fun onGetHistory(history: Map<Long, Double>)
        fun onFail(exception: Exception)
    }

    interface Presenter {
        fun getDetail(coin: Coin)
    }
}