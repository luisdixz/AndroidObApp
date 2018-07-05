package com.diazmain.obapp.Home.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diazmain.obapp.R
import im.dacer.androidcharts.LineView
import kotlinx.android.synthetic.main.fragment_progress.*


class ProgressFragment: Fragment() {

    companion object {
        fun newInstance() = ProgressFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //fillGraphExample()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
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