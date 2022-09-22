package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import com.hmdlong14.cryptocurrency.data.model.Coin
import org.json.JSONObject

class CoinDetailParser : Parser<MutableList<Coin>>() {
    override fun parseData(jsonObject: JSONObject): MutableList<Coin> {
        return mutableListOf<Coin>().apply {
            add(Coin.parseJsonToCoinWithDetail(jsonObject.getJSONObject(ParseDetailParam.COIN_KEY)))
        }
    }
}

object ParseDetailParam {
    const val COIN_KEY = "coin"
}
