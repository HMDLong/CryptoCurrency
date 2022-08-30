package com.hmdlong14.cryptocurrency.data.repository.sources

interface CoinSource {
    interface Local {
        fun getLocalCoinData(callback: CoinRepoCallback)
    }

    interface Remote {
        fun getCoinsData(callback: CoinRepoCallback)
        fun getCoinDetail(uuid : String, callback: CoinRepoCallback)
        fun searchCoins(queryText : String, callback: CoinRepoCallback)
    }
}
