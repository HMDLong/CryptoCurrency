package com.hmdlong14.cryptocurrency.data.repository.sources.local

import android.content.Context
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.local.favorites.FavoritesSource

class LocalCoinSource : CoinSource.Local {
    override fun getFavoriteCoins(context: Context, callback: LocalCoinCallback) {
        try {
            callback.onSuccess(FavoritesSource.getInstance(context).favorites)
        } catch (e : Exception) {
            callback.onFailed(e)
        }
    }

    override fun getHoldingCoins(context: Context, callback: LocalCoinCallback) {
        // TODO implement later
    }
}
