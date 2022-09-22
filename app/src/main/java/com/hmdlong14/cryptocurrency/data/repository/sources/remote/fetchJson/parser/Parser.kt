package com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser

import org.json.JSONObject

abstract class Parser<T> {
    abstract fun parseData(jsonObject: JSONObject) : T
}
