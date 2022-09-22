package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import org.json.JSONObject

class HistoryParser : Parser<List<HistoryEntry<Double>>>() {
    override fun parseData(jsonObject: JSONObject): List<HistoryEntry<Double>> {
        val historyJSONArray = jsonObject.getJSONArray(HISTORY_KEY)
        val res = mutableListOf<HistoryEntry<Double>>().apply {
            for(i in 0 until historyJSONArray.length()){
                add(HistoryEntry(
                        historyJSONArray.getJSONObject(i).getLong(TIMESTAMP_KEY),
                        historyJSONArray.getJSONObject(i).getDouble(ENTRY_DATA_KEY)
                    )
                )
            }
        }
        return res
    }

    companion object {
        const val HISTORY_KEY = "history"

        const val TIMESTAMP_KEY = "timestamp"
        const val ENTRY_DATA_KEY = "price"
    }
}

data class HistoryEntry<T>(val timestamp: Long, val data: T)