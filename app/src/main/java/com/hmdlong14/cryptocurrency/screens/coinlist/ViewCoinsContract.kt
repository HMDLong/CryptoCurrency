package com.hmdlong14.cryptocurrency.screens.coinlist

import android.content.Context
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.model.CoinEntry
import com.hmdlong14.cryptocurrency.data.repository.StateKey

interface ViewCoinsContract {
    interface View {
        fun onGetCoinsSuccess(coins : MutableList<Coin>)
        fun onGetMoreCoinsSuccess(coins: MutableList<Coin>)
        fun onGetCoinFailed(exception: Exception)
    }

    interface Presenter {
        fun getCoins(state : Map<StateKey, Any>)
        fun getFavoriteCoins(context: Context)
        fun searchCoins(state : Map<StateKey, Any>)
        fun getMoreCoins(state : Map<StateKey, Any>)
    }
}
