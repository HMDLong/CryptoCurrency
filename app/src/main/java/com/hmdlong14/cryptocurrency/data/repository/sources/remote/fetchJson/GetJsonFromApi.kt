package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson

import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.hmdlong14.cryptocurrency.data.repository.sources.CoinRepoCallback
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.CoinDetailParser
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.CoinsParser
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.Parser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class GetJsonFromApi {
    private var mExecutor = Executors.newSingleThreadExecutor()
    private var mHandler = Handler(Looper.getMainLooper())

    fun getCoinsFromApi(offset: Int, setOffsetHook: (Int) -> Unit, callback: CoinRepoCallback){
        val uri = Uri.parse(BASE_URI + COINS_RESOURCE).buildUpon()
            .appendQueryParameter(QUERY_LIMIT, QUERY_LIMIT_VALUE.toString())
            .appendQueryParameter(OFFSET, offset.toString())
            .build()
        getJsonFromApi(uri.toString(), CoinsParser(), callback)
        setOffsetHook(offset + QUERY_LIMIT_VALUE)
    }

    fun getCoinDetailFromApi(uuid: String, callback: CoinRepoCallback){
        val uri = Uri.parse(BASE_URI + COIN_DETAIL_RESOURCE.replace(":uuid", uuid))
        getJsonFromApi(uri.toString(), CoinDetailParser(), callback)
    }

    fun searchCoins(query: String, callback: CoinRepoCallback){
        val uri = Uri.parse(BASE_URI + COINS_RESOURCE).buildUpon()
            .appendQueryParameter(QUERY_LIMIT, QUERY_LIMIT_VALUE.toString())
            .appendQueryParameter(SEARCH, query)
            .build()
        getJsonFromApi(uri.toString(), CoinsParser(), callback)
    }

    private fun getJsonFromApi(url: String, parser: Parser, callback: CoinRepoCallback){
        mExecutor.execute {
            val connection =
                (URL(url).openConnection() as HttpURLConnection).apply {
                    readTimeout = READ_TIMEOUT
                    requestMethod = METHOD_GET
                    connectTimeout = CONNECTION_TIMEOUT
                    doInput = true
                    setRequestProperty(API_KEY_HEADER_KEY, API_KEY)
                }
            connection.connect()
            val stringJson = parseResponseToString(connection.inputStream)
            connection.disconnect()
            val res = parser.parseData(JSONObject(stringJson).getJSONObject(DATA_KEY))
            mHandler.post {
                try {
                    callback.onSuccess(res)
                } catch(e : Exception){
                    callback.onFailed(e)
                }
            }
        }
    }

    private fun parseResponseToString(inputStream: InputStream) : String {
        val stringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).apply {
            forEachLine { line ->
                stringBuilder.append(line)
            }
            close()
        }
        return stringBuilder.toString()
    }

    companion object {
        const val BASE_URI = "https://api.coinranking.com/v2"
        const val COINS_RESOURCE = "/coins"
        const val COIN_DETAIL_RESOURCE = "/coin/:uuid"

        const val API_KEY = "coinrankingcffc42979ab3727a515035f5de5c7533a6e8b9789b3bc76f"
        const val API_KEY_HEADER_KEY = "x-access-token"
        const val READ_TIMEOUT = 10000
        const val CONNECTION_TIMEOUT = 15000
        const val METHOD_GET = "GET"

        const val QUERY_LIMIT = "limit"
        const val QUERY_LIMIT_VALUE = 100
        const val OFFSET = "offset"
        const val SEARCH = "search"

        const val DATA_KEY = "data"
    }
}
