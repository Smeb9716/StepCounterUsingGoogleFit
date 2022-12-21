package com.lhd.fit

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.gms.fitness.data.*
import java.util.*
import kotlin.collections.ArrayList


const val TAG = "BasicSensorsApi"

class MainActivity2 : AppCompatActivity() {

    private val lsAxis = ArrayList<String>()
    private val lsBarEntry = ArrayList<BarEntry>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        addDataChar()
        val chart = findViewById<BarChart>(R.id.barChart)

        val dataSet = BarDataSet(lsBarEntry, "").apply {
            setDrawValues(false)
            valueTextSize = 14f
        }
        val dataChart = BarData(dataSet)

        chart.apply {
            data = dataChart
            legend.isEnabled = false
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            animateY(3000, Easing.EaseOutBack)
            extraBottomOffset = 10f
            extraLeftOffset = 2f
            notifyDataSetChanged()
            invalidate()
//            renderer = myBarChartRender
            setNoDataText("Loading...")
            setTouchEnabled(true)
            setScaleEnabled(false)
            setVisibleXRangeMaximum(7F)
        }
        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = IndexAxisValueFormatter(lsAxis)
            textColor = Color.WHITE
            textSize = 12f
            setDrawAxisLine(false)
            setDrawGridLines(false)
            granularity = 1f

        }
    }

    private fun addDataChar() {
        lsAxis.add("0")
        lsAxis.add("1-223")
        lsAxis.add("2-123")
        lsAxis.add("3-34")
        lsAxis.add("4-434")
        lsAxis.add("5-34")
//        lsAxis.add("6")
        //
        lsBarEntry.add(BarEntry(1f, 1f))
        lsBarEntry.add(BarEntry(2f, 2f))
        lsBarEntry.add(BarEntry(3f, 3f))
        lsBarEntry.add(BarEntry(4f, 0f))
        lsBarEntry.add(BarEntry(5f, 0f))
    }

}
