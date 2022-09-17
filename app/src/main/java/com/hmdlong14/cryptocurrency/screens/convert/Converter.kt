package com.hmdlong14.cryptocurrency.screens.convert

import com.hmdlong14.cryptocurrency.data.model.Coin

class Converter {
    lateinit var source : Coin
    lateinit var target : Coin

    fun convert(amount : Double) : Double? {
        if(target.price == null || source.price == null)
            throw Exception("null information")
        return source.price!! * amount / target.price!!
    }
}