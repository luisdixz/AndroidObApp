package com.diazmain.obapp.Threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.diazmain.obapp.Home.model.CitasValue
import com.diazmain.obapp.R
import com.diazmain.obapp.helper.SharedPrefManager
import java.util.*

class UpdateHomeAppointUI(_context: Context) : AsyncTask<View, Void, Boolean>() {

    @SuppressLint("StaticFieldLeak")
    private val context = _context
    @SuppressLint("StaticFieldLeak")
    private lateinit var llNextAppo: LinearLayout
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvAppoDate: TextView

    private lateinit var cita: CitasValue

    override fun doInBackground(vararg params: View?): Boolean {
        llNextAppo = params[0] as LinearLayout
        tvAppoDate = params[1] as TextView

        if (SharedPrefManager.getInstance(context)!!.isAppointAvaliable()){
            cita = SharedPrefManager.getInstance(context)?.getAppoint()!!
            //Log.w("Location", "AppointmentAvaliable -> doInBackground")
            return true
        } else {
            //Log.w("Location", "AppointmentNoAvaliable -> doInBackground")
            return false
        }
    }

    override fun onPostExecute(result: Boolean) {
        //Log.w("Location", "onPostExecute -> UpdateHomeAppointmentUI")
        if (result) {
            llNextAppo.visibility = View.VISIBLE
            val date: String = cita.fecha +" - a las "+cita.hora+" hrs"
            tvAppoDate.setText(date)
        } else {
            llNextAppo.visibility = View.GONE
            tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))
        }
    }

}