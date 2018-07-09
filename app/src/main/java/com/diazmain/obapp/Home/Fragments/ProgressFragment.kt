package com.diazmain.obapp.Home.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.Home.HomeActivity
import com.diazmain.obapp.R
import im.dacer.androidcharts.LineView
import kotlinx.android.synthetic.main.fragment_progress.*
import java.text.DecimalFormat
import java.text.NumberFormat


class ProgressFragment: Fragment() {

    companion object {
        fun newInstance() = ProgressFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //(activity as HomeActivity).measuresList

        //fillGraphExample()
        fillGraph()
        updateProgresIndicators()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    fun fillGraph() {
        //Bottom label
        val cant: Int = (activity as HomeActivity).measuresList.size
        var cont: Int = 0

        val bottomLabels: ArrayList<String> = ArrayList()
        while (cont != cant) {
            bottomLabels.add((cont+1).toString())
            cont++
        }

        val peso: ArrayList<Float> = ArrayList()
        val grasa: ArrayList<Float> = ArrayList()
        val cintura: ArrayList<Float> = ArrayList()
        for (i in (activity as HomeActivity).measuresList.indices) {
            peso.add((activity as HomeActivity).measuresList[i].peso.toFloat())
            grasa.add((activity as HomeActivity).measuresList[i].grasa.toFloat())
            cintura.add((activity as HomeActivity).measuresList[i].cintura.toFloat())
        }

        val data1: ArrayList<ArrayList<Float>> = ArrayList()
        data1.add(peso)
        data1.add(cintura)
        data1.add(grasa)

        val lineView: LineView = line_view
        line_view.setDrawDotLine(true)
        line_view.setShowPopup(LineView.SHOW_POPUPS_All)
        line_view.setBottomTextList(bottomLabels)
        //line_view.setColorArray(intArrayOf(Color.BLACK, Color.RED, Color.MAGENTA))
        line_view.setColorArray(intArrayOf(resources.getColor(R.color.black), resources.getColor(R.color.red), resources.getColor(R.color.lightBlue)))
        line_view.setFloatDataList(data1)
    }

    fun updateProgresIndicators() {

        var progPeso: Double = (activity as HomeActivity).measuresList[0].peso.toDouble() - (activity as HomeActivity).measuresList.last().peso.toDouble()
        var progCint: Double = (activity as HomeActivity).measuresList[0].cintura.toDouble() - (activity as HomeActivity).measuresList.last().cintura.toDouble()
        var progGras: Double = (activity as HomeActivity).measuresList[0].grasa.toDouble() - (activity as HomeActivity).measuresList.last().grasa.toDouble()

        if (progPeso < 0) {
            progPeso *= -1
            imProgressWeigthTrend.setBackgroundResource(R.drawable.ic_trending_up)
        } else {
            imProgressWeigthTrend.setBackgroundResource(R.drawable.ic_trending_down)
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

        tvProgressWeigthPercent.setText(formater.format(progPeso).toString() + getString(R.string.label_kilo))
        tvProgressWaistPercent.setText(formater.format(progCint).toString() + getString(R.string.label_centimeter))
        tvProgressFatPercent.setText(formater.format(progGras).toString() + getString(R.string.label_percent))
    }

    fun fillGraphExample() {
        val bottomLabels: ArrayList<String> = ArrayList()
        bottomLabels.add("1")
        bottomLabels.add("2")
        bottomLabels.add("3")
        bottomLabels.add("4")
        bottomLabels.add("5")
        bottomLabels.add("6")
        bottomLabels.add("7")
        bottomLabels.add("8")

        val peso: ArrayList<Float> = ArrayList()
        peso.add(56.1F)
        peso.add(54.8F)
        peso.add(55.2F)
        peso.add(59.3F)

        val cintura: ArrayList<Float> = ArrayList()
        cintura.add(62.1F)
        cintura.add(59.2F)
        cintura.add(60.1F)
        //peso.add(65.3F)

        val grasa: ArrayList<Float> = ArrayList()
        grasa.add(12F)
        grasa.add(11.2F)
        grasa.add(13.1F)
        grasa.add(15.5F)

        val data1: ArrayList<ArrayList<Float>> = ArrayList()
        data1.add(peso)
        data1.add(cintura)
        data1.add(grasa)

        val lineView: LineView = line_view
        line_view.setDrawDotLine(true)
        line_view.setShowPopup(LineView.SHOW_POPUPS_All)
        line_view.setBottomTextList(bottomLabels)
        line_view.setColorArray(intArrayOf(Color.BLACK, Color.RED, Color.MAGENTA))
        line_view.setFloatDataList(data1)
    }

}