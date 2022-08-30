package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import com.hmdlong14.cryptocurrency.data.model.Coin
import org.json.JSONObject

abstract class Parser {
    abstract fun parseData(jsonObject: JSONObject) : MutableList<Coin>
}
