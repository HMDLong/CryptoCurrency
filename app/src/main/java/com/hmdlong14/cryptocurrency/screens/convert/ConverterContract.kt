package com.hmdlong14.cryptocurrency.screens.convert

import com.hmdlong14.cryptocurrency.data.model.Coin
import java.lang.Exception

interface ConverterContract {
    interface Presenter {
        fun init()
        fun setSource(source: Coin)
        fun setTarget(target: Coin)
        fun convert(amount: Double) : Double
    }

    interface View {
        fun onSetCoinSuccess(source: Coin? = null, target: Coin? = null, coins: List<Coin>? = null)
        fun onSetCoinFailed(exception: Exception)
        fun onResultSuccess(result : Double)
        fun onResultFailed(exception: Exception)
    }
}