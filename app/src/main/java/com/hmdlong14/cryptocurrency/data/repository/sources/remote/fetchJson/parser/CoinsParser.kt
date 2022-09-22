package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import com.hmdlong14.cryptocurrency.data.model.Coin
import org.json.JSONObject

class CoinsParser : Parser<MutableList<Coin>>() {
    override fun parseData(jsonObject: JSONObject) : MutableList<Coin> {
        val coins = mutableListOf<Coin>()
        val coinsJSONArray = jsonObject
            .getJSONArray(ParseParam.COINS_KEY)
        for(i in 0 until coinsJSONArray.length()){
            coins.add(Coin.parseJsonToCoin(coinsJSONArray.getJSONObject(i)))
        }
        return coins
    }
}

object ParseParam {
    const val COINS_KEY = "coins"
}
