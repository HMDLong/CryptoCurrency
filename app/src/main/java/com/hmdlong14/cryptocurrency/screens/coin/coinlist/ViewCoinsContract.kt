package com.hmdlong14.cryptocurrency.screens.coin.coinlist

import android.content.Context
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.StateKey

interface ViewCoinsContract {
    interface View {
        fun onGetCoinsSuccess(coins : MutableList<Coin>)
        fun onGetMoreCoinsSuccess(coins: MutableList<Coin>)
        fun onGetCoinFailed(exception: Exception)
    }

    interface Presenter {
        fun getCoins(state : Map<StateKey, Any>? = null)
        fun getFavoriteCoins(context: Context)
        fun searchCoins(state : Map<StateKey, Any>? = null)
        fun getMoreCoins(state : Map<StateKey, Any>? = null)
    }
}
