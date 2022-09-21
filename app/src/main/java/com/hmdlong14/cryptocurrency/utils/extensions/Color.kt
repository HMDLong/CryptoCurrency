package com.hmdlong14.cryptocurrency.utils.extensions

import android.graphics.Color

private const val colorStep = 26

fun getTriGradient(color: Int) : IntArray {
    val r1 = Color.red(color)
    val g1 = Color.green(color)
    val b1 = Color.blue(color)
    val palette = mutableListOf(0, 0, 0).apply {
        this[listOf(r1, g1, b1).indexOf(listOf(r1, g1, b1).maxOrNull())] = 1
    }
    return intArrayOf(
        color,
        Color.rgb(r1 - colorStep*palette[0], g1 - colorStep*palette[1], b1 - colorStep*palette[2]),
        Color.rgb(r1 - colorStep*2*palette[0], g1 - colorStep*2*palette[1], b1 - colorStep*2*palette[2]),
    )
}
