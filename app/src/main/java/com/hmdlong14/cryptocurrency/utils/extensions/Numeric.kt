package com.hmdlong14.cryptocurrency.utils.extensions

import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.roundToInt

const val base = 10.0
const val threshold = 0.0001
const val recursiveLimit = 10

fun Double.round(decimal: Int) : Double {
    if(decimal > recursiveLimit) return this
    val divisor = base.pow(decimal)
    val res = (this * divisor).roundToInt() / divisor
    return if(res > threshold) res else this.round(decimal+1)
}

fun Number.roundUpToBn() : Float {
    val exp = (ln(this.toDouble()) / ln(1_000_000_000F)).toInt()
    return this.toFloat() / 1_000_000_000F.pow(exp)
}
