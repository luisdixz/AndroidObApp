package com.diazmain.obapp.Home.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.diazmain.obapp.Home.model.MeasuresValue

class HomeSharedPrefManager (private val mCtx: Context) {

    companion object {
        private val SHARED_PREF_NAME = "generalhomescreendatafromserver"

        private val KEY_ALL_MEASURES = "keyallmeasures"

        @SuppressLint("StaticFieldLeak")
        private var mInstance: HomeSharedPrefManager? = null

        fun getInstance(context: Context): HomeSharedPrefManager? {
            synchronized(this) {
                if (mInstance == null) {
                    mInstance = HomeSharedPrefManager(context);
                }
            }

            return mInstance
        }
    }

    fun storeMeasures(measures: List<MeasuresValue>) {
        val sPref: SharedPreferences? = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        //val editor: SharedPreferences.Editor = sPref.edit()

        //editor.put

    }

}