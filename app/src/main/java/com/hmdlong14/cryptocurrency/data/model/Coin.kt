package com.hmdlong14.cryptocurrency.data.model

import android.os.Build
import android.text.Html
import androidx.core.text.HtmlCompat
import org.json.JSONObject

class Coin {
    var uuid : String = ""
    var symbol: String = ""
    var name : String = ""
    var color: String = ""
    var iconUrl: String = ""
    var marketCap: Long = 0L
    var price: Double = 0.0
    var tier: Int = 0
    var rank: Int = 0
    var description = ""
    var sparklines = mutableListOf<Double>()

    companion object {
        fun parseJsonToCoin(jsonObject: JSONObject) = Coin().apply {
            uuid = jsonObject.getString(CoinEntry.UUID)
            symbol = jsonObject.getString(CoinEntry.SYMBOL)
            name = jsonObject.getString(CoinEntry.NAME)
            color = jsonObject.getString(CoinEntry.COLOR)
            iconUrl = jsonObject.getString(CoinEntry.ICON)
            marketCap = jsonObject.getLong(CoinEntry.MARKET_CAP)
            price = jsonObject.getDouble(CoinEntry.PRICE)
            tier = jsonObject.getInt(CoinEntry.TIER)
            rank = jsonObject.getInt(CoinEntry.RANK)
        }

        fun parseJsonToCoinWithDetail(jsonObject: JSONObject) =
            parseJsonToCoin(jsonObject).also { coin ->
                coin.description = jsonObject.getString(CoinEntry.DESCRIPTION).let { htmlDescription ->
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(htmlDescription, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                    } else {
                        Html.fromHtml(htmlDescription).toString()
                    }
                }
                val sparkline = jsonObject.getJSONArray(CoinEntry.SPARKLINE)
                coin.sparklines.apply {
                    clear()
                    for(i in 0 until sparkline.length()){
                        add(sparkline.getDouble(i))
                    }
                }
            }
    }
}

object CoinEntry {
    const val UUID = "uuid"
    const val SYMBOL = "symbol"
    const val NAME = "name"
    const val COLOR = "color"
    const val ICON = "iconUrl"
    const val MARKET_CAP = "marketCap"
    const val PRICE = "price"
    const val TIER = "tier"
    const val RANK = "rank"
    const val DESCRIPTION = "description"
    const val SPARKLINE = "sparkline"
}
