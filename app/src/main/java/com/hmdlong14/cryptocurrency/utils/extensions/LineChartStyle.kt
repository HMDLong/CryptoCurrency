package com.hmdlong14.cryptocurrency.utils.extensions

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat
import java.util.*

fun LineChart.applyCustomStyle() {
    axisRight.isEnabled = false

    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        valueFormatter = CustomXAxisFormatter()
    }
}

fun LineDataSet.applyCustomStyle() : LineDataSet {
    mode = LineDataSet.Mode.CUBIC_BEZIER
    setDrawValues(false)
    return this
}

class CustomXAxisFormatter : IndexAxisValueFormatter(){
    override fun getFormattedValue(value: Float): String {
        val millisecTimestamp = value.toLong() * 1000
        val date = Date(millisecTimestamp)
        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateFormatter.format(date)
    }
}
