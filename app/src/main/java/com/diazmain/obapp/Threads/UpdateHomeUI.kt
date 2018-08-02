package com.diazmain.obapp.threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.diazmain.obapp.home.model.LastMeasures
import com.diazmain.obapp.R
import com.diazmain.obapp.helper.SharedPrefManager

class UpdateHomeUI(_apContext: Context) : AsyncTask<View, Void, LastMeasures>() {

    @SuppressLint("StaticFieldLeak")
    private val apContext = _apContext

    @SuppressLint("StaticFieldLeak")
    private lateinit var tvResWeight: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvResWaist: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvResFat: TextView

    @SuppressLint("StaticFieldLeak")
    lateinit var imWeightLittleProgress: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imWaistLittleProgress: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imFatLittleProgress: ImageView

    @SuppressLint("StaticFieldLeak")
    lateinit var tvPreviousWeight: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvPreviousWaist: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvPreviousFat: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvNextWeight: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvNextWaist: TextView
    @SuppressLint("StaticFieldLeak")
    lateinit var tvNextFat: TextView

    @SuppressLint("StaticFieldLeak")
    lateinit var imWeightState: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imWaistState: ImageView
    @SuppressLint("StaticFieldLeak")
    lateinit var imFatState: ImageView

    override fun doInBackground(vararg params: View?): LastMeasures {
        tvResWeight = params[0] as TextView
        tvResWaist = params[1] as TextView
        tvResFat = params[2] as TextView

        imWeightLittleProgress =  params[3] as ImageView
        imWaistLittleProgress = params[4] as ImageView
        imFatLittleProgress = params[5] as ImageView

        tvPreviousWeight= params[6] as TextView
        tvPreviousWaist = params[7] as TextView
        tvPreviousFat = params[8] as TextView
        tvNextWeight = params[9] as TextView
        tvNextWaist = params[10] as TextView
        tvNextFat = params[11] as TextView

        imWeightState = params[12] as ImageView
        imWaistState = params[13] as ImageView
        imFatState = params[14] as ImageView

        return SharedPrefManager.getInstance(apContext)!!.getLastMeasures()
    }

    override fun onPostExecute(result: LastMeasures) {
        when {
            result.months == 0 -> {
                tvResWeight.setText(apContext.getString(R.string.label_prog_anuncio_default))
                tvResWaist.setText(apContext.getString(R.string.label_prog_anuncio_default))
                tvResFat.setText(apContext.getString(R.string.label_prog_anuncio_default))
            }
            result.months == 1 -> {
                tvResWeight.setText(apContext.getString(R.string.label_prog_anuncio_default))
                tvResWaist.setText(apContext.getString(R.string.label_prog_anuncio_default))
                tvResFat.setText(apContext.getString(R.string.label_prog_anuncio_default))

                imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
            }
            else -> {
                when {
                    result.lastMonth.cintura < result.currentMonth.cintura -> {
                        tvResWaist.setText(apContext.getString(R.string.label_prog_up))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                        imWaistState.setBackgroundResource(R.drawable.official_sad)
                    }
                    result.lastMonth.cintura > result.currentMonth.cintura -> {
                        tvResWaist.setText(apContext.getString(R.string.label_prog_down))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imWaistState.setBackgroundResource(R.drawable.official_smiley)
                    }
                    else -> {
                        tvResWaist.setText(apContext.getString(R.string.label_prog_stay))
                        imWaistLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imWaistState.setBackgroundResource(R.drawable.official_smiley)
                    }
                }
                when {
                    result.lastMonth.peso < result.currentMonth.peso -> {
                        tvResWeight.setText(apContext.getString(R.string.label_prog_up))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                        imWeightState.setBackgroundResource(R.drawable.official_sad)
                    }
                    result.lastMonth.peso > result.currentMonth.peso -> {
                        tvResWeight.setText(apContext.getString(R.string.label_prog_down))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imWeightState.setBackgroundResource(R.drawable.official_smiley)
                    }
                    else -> {
                        tvResWeight.setText(apContext.getString(R.string.label_prog_stay))
                        imWeightLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imWeightState.setBackgroundResource(R.drawable.official_smiley)
                    }
                }
                when {
                    result.lastMonth.grasa < result.currentMonth.grasa -> {
                        tvResFat.setText(apContext.getString(R.string.label_prog_up))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_subida)
                        imFatState.setBackgroundResource(R.drawable.official_sad)
                    }
                    result.lastMonth.grasa > result.currentMonth.grasa -> {
                        tvResFat.setText(apContext.getString(R.string.label_prog_down))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imFatState.setBackgroundResource(R.drawable.official_smiley)
                    }
                    else -> {
                        tvResFat.setText(apContext.getString(R.string.label_prog_stay))
                        imFatLittleProgress.setBackgroundResource(R.drawable.icono_bajada)
                        imFatState.setBackgroundResource(R.drawable.official_smiley)
                    }
                }
            }
        }

        tvPreviousWeight.setText(result.lastMonth.peso.toString() + apContext.getString(R.string.label_kilo))
        tvNextWeight.setText(result.currentMonth.peso.toString() + apContext.getString(R.string.label_kilo))
        tvPreviousWaist.setText(result.lastMonth.cintura.toString() + apContext.getString(R.string.label_centimeter))
        tvNextWaist.setText(result.currentMonth.cintura.toString() + apContext.getString(R.string.label_centimeter))
        tvPreviousFat.setText(result.lastMonth.grasa.toString() + apContext.getString(R.string.label_percent))
        tvNextFat.setText(result.currentMonth.grasa.toString() + apContext.getString(R.string.label_percent))
    }
}