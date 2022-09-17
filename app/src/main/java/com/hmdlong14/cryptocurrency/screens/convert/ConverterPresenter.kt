package com.hmdlong14.cryptocurrency.screens.convert

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.CoinResultCallback

class ConverterPresenter(private val repository: CoinRepository) : ConverterContract.Presenter {
    private val converter : Converter by lazy { Converter() }
    var view : ConverterContract.View? = null

    override fun init() {
        repository.getCacheCoin(object : CoinResultCallback {
            override fun onSuccess(coins: MutableList<Coin>) {
                setSource(coins[0])
                setTarget(coins[1])
                view?.onSetCoinSuccess(coins[0], coins[1], coins)
            }

            override fun onFailed(exception: Exception) {
                view?.onSetCoinFailed(exception)
            }
        })
    }

    override fun setSource(source: Coin) {
        converter.source = source
    }

    override fun setTarget(target: Coin) {
        converter.target = target
    }

    override fun convert(amount: Double) : Double {
        converter.convert(amount)?.let { view?.onResultSuccess(it) }
        return converter.convert(amount) ?: 0.0
    }
}
