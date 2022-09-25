package com.hmdlong14.cryptocurrency.utils.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.hmdlong14.cryptocurrency.R
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun LineChart.applyCustomStyle() {
    axisRight.isEnabled = false
    axisLeft.setDrawGridLinesBehindData(false)
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        valueFormatter = CustomXAxisFormatter()
        setDrawAxisLine(false)
        setDrawGridLines(false)
    }
}

fun LineDataSet.applyCustomStyle(ctx : Context) : LineDataSet {
    mode = LineDataSet.Mode.CUBIC_BEZIER
    setDrawCircles(false)
    setDrawValues(false)
    setDrawFilled(true)
    fillDrawable = AppCompatResources.getDrawable(ctx, R.drawable.chart_fill_gradient)
    return this
}

class CustomXAxisFormatter : IndexAxisValueFormatter(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFormattedValue(value: Float): String {
        val dateTime = LocalDateTime.ofEpochSecond(value.toLong(), 0, ZoneOffset.UTC)
        val dateTimeFormatter = DateTimeFormatter.ofPattern(X_DATETIME_PATTERN, Locale.getDefault())
        return dateTime.format(dateTimeFormatter)
    }

    companion object {
        const val X_DATETIME_PATTERN = "MM/dd"
    }
}
