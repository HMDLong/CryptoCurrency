package com.hmdlong14.cryptocurrency.data.model

import android.os.Build
import android.text.Html
import androidx.core.text.HtmlCompat
import org.json.JSONObject
import java.io.Serializable

class Coin : Serializable {
    var uuid : String = ""
    var symbol: String = ""
    var name : String = ""
    var color: String = ""
    var iconUrl: String = ""
    var marketCap: Long? = 0
    var price: Double? = 0.0
    var change : Double? = 0.0
    var volume24h: Long? = 0
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
            var cancelVote = 0
            marketCap = run {
                try {
                    jsonObject.getString(CoinEntry.MARKET_CAP).toLong()
                } catch (e : NumberFormatException) {
                    cancelVote += 1
                    null
                }
            }
            price = run {
                try {
                    jsonObject.getString(CoinEntry.PRICE).toDouble()
                } catch (e : NumberFormatException) {
                    cancelVote += 1
                    null
                }
            }
            volume24h = run {
                try {
                    jsonObject.getString(CoinEntry.VOLUME_24H).toLong()
                } catch (e : NumberFormatException) {
                    cancelVote += 1
                    null
                }
            }
            change = run {
                try {
                    jsonObject.getString(CoinEntry.CHANGE).toDouble()
                } catch (e : NumberFormatException) {
                    cancelVote += 1
                    null
                }
            }
            tier = jsonObject.getInt(CoinEntry.TIER)
            rank = jsonObject.getInt(CoinEntry.RANK)
            if(cancelVote > 2)
                throw Exception()
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
    const val CHANGE = "change"
    const val VOLUME_24H = "24hVolume"
    const val TIER = "tier"
    const val RANK = "rank"
    const val DESCRIPTION = "description"
    const val SPARKLINE = "sparkline"
}
