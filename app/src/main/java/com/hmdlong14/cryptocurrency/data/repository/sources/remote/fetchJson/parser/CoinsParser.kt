package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.utils.exceptions.CancelCoinException
import org.json.JSONObject
import java.lang.Exception

class CoinsParser : Parser<MutableList<Coin>>() {
    override fun parseData(jsonObject: JSONObject) : MutableList<Coin> {
        val coins = mutableListOf<Coin>()
        val coinsJSONArray = jsonObject
            .getJSONArray(ParseParam.COINS_KEY)
        for(i in 0 until coinsJSONArray.length()){
            try {
                coins.add(Coin.parseJsonToCoin(coinsJSONArray.getJSONObject(i)))
            } catch (e : CancelCoinException) {
                continue
            }
        }
        return coins
    }
}

object ParseParam {
    const val COINS_KEY = "coins"
}
