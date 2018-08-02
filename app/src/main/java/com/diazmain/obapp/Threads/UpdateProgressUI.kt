package com.diazmain.obapp.threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.diazmain.obapp.home.model.MeasuresValue
import com.diazmain.obapp.R
import im.dacer.androidcharts.LineView
import java.text.DecimalFormat
import java.text.NumberFormat

class UpdateProgressUI(_apContext: Context, _measures: List<MeasuresValue>) : AsyncTask<View, Void, Void>() {

    @SuppressLint("StaticFieldLeak")
    private val apContext: Context = _apContext
    private val measuresList: List<MeasuresValue> = _measures

    @SuppressLint("StaticFieldLeak")
    lateinit var lineView : LineView
    @SuppressLint("StaticFieldLeak")
    lateinit var imProgressWeightTrend: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imProgressWaistTrend: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imProgressFatTrend: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvProgressWeightPercent: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvProgressWaistPercent: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvProgressFatPercent: TextView

    override fun doInBackground(vararg params: View?): Void? {
        lineView = params[0] as LineView

        imProgressWeightTrend = params[1] as ImageView
        imProgressWaistTrend = params[2] as ImageView
        imProgressFatTrend = params[3] as ImageView

        tvProgressWeightPercent = params[4] as TextView
        tvProgressWaistPercent = params[5] as TextView
        tvProgressFatPercent = params[6] as TextView

        return null
    }

    override fun onPostExecute(result: Void?) {
        //Fill graph
        val cant: Int = measuresList.size
        var cont: Int = 0

        val bottomLabels: ArrayList<String> = ArrayList()
        while (cont != cant) {
            bottomLabels.add((cont+1).toString())
            cont++
        }

        val peso: ArrayList<Float> = ArrayList()
        val grasa: ArrayList<Float> = ArrayList()
        val cintura: ArrayList<Float> = ArrayList()
        for (i in measuresList.indices) {
            peso.add(measuresList[i].peso.toFloat())
            grasa.add(measuresList[i].grasa.toFloat())
            cintura.add(measuresList[i].cintura.toFloat())
        }

        val data1: ArrayList<ArrayList<Float>> = ArrayList()
        data1.add(peso)
        data1.add(cintura)
        data1.add(grasa)

        val lineView: LineView = lineView
        lineView.setDrawDotLine(true)
        lineView.setShowPopup(LineView.SHOW_POPUPS_All)
        lineView.setBottomTextList(bottomLabels)
        lineView.setColorArray(
                intArrayOf(
                        apContext.resources.getColor(R.color.black),
                        apContext.resources.getColor(R.color.red),
                        apContext.resources.getColor(R.color.lightBlue)
                ))
        lineView.setFloatDataList(data1)

        //fill info
        if (measuresList.size != 0) {
            var progPeso: Double = measuresList[0].peso.toDouble() - measuresList.last().peso.toDouble()
            var progCint: Double = measuresList[0].cintura.toDouble() - measuresList.last().cintura.toDouble()
            var progGras: Double = measuresList[0].grasa.toDouble() - measuresList.last().grasa.toDouble()

            if (progPeso < 0) {
                progPeso *= -1
                imProgressWeightTrend.setBackgroundResource(R.drawable.ic_trending_up)
            } else {
                imProgressWeightTrend.setBackgroundResource(R.drawable.ic_trending_down)
            }
            if (progCint < 0) {
                progCint *= -1
                imProgressWaistTrend.setBackgroundResource(R.drawable.ic_trending_up)
            } else {
                imProgressWaistTrend.setBackgroundResource(R.drawable.ic_trending_down)
            }
            if (progGras < 0) {
                progGras *= -1
                imProgressFatTrend.setBackgroundResource(R.drawable.ic_trending_up)
            } else {
                imProgressFatTrend.setBackgroundResource(R.drawable.ic_trending_down)
            }

            val formater: NumberFormat = DecimalFormat("#0.00")

            tvProgressWeightPercent.setText(formater.format(progPeso).toString() + apContext.getString(R.string.label_kilo))
            tvProgressWaistPercent.setText(formater.format(progCint).toString() + apContext.getString(R.string.label_centimeter))
            tvProgressFatPercent.setText(formater.format(progGras).toString() + apContext.getString(R.string.label_percent))


        }
    }

}