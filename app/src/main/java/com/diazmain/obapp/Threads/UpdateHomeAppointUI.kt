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
import java.text.SimpleDateFormat


class UpdateHomeAppointUI(_context: Context) : AsyncTask<View, Void, CitasValue>() {

    @SuppressLint("StaticFieldLeak")
    private val context = _context
    @SuppressLint("StaticFieldLeak")
    private lateinit var llNextAppo: LinearLayout
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvAppoDate: TextView

    private lateinit var cita: CitasValue

    override fun doInBackground(vararg params: View?): CitasValue {
        llNextAppo = params[0] as LinearLayout
        tvAppoDate = params[1] as TextView

        //if (SharedPrefManager.getInstance(context)!!.isAppointAvaliable()){
            cita = SharedPrefManager.getInstance(context)?.getAppoint()!!
            //Log.w("Location", "AppointmentAvaliable -> doInBackground")
            return cita
        /*} else {
            //Log.w("Location", "AppointmentNoAvaliable -> doInBackground")
            return false
        }*/
    }

    override fun onPostExecute(result: CitasValue) {
        Log.w("Location", "onPostExecute -> UpdateHomeAppointmentUI")
        //Log.w("Result", result.toString())

        when (result.status) {
            0 -> {
                llNextAppo.visibility = View.GONE
                tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))
            }
            1 -> {
                llNextAppo.visibility = View.VISIBLE
                val date: String = cita.fecha +" - a las "+cita.hora+" hrs"
                tvAppoDate.setText(date)
            }
            2 -> {
                llNextAppo.visibility = View.GONE
                if (isApponitOutdate(cita)) {
                    tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))
                } else {
                    tvAppoDate.setText("Completa el formulario")
                }
            }
        }
        /*val currentTime = Calendar.getInstance().time

        val timeFormat = SimpleDateFormat("HH:mm:ss")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val date: Date = dateFormat.parse(cita.fecha)
        val time: Date = timeFormat.parse(cita.hora)

        //Log.w("currentTime.after", "currentTime.after -> " + currentTime.after(date))
        //Log.w("currentTime.after", "currentTime.after -> " + currentTime.after(time))
        //Log.w("currentTime", currentTime.toString())
        Log.w("currentTime", System.currentTimeMillis().toString())
        Log.w("date", date.toString())
        Log.w("time", time.toString())
 ,
        // true = la fecha proporcionada pasado con respecto a la actualidad
        Log.w("currentTime > date", (System.currentTimeMillis() > date.time).toString() )
        Log.w("currentTime > date", (System.currentTimeMillis() > time.time).toString() )*/


    }

    // true = appoint is outdated
    fun isApponitOutdate(cita: CitasValue) : Boolean {
        val currentTime = Calendar.getInstance().time

        val timeFormat = SimpleDateFormat("HH:mm:ss")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val date: Date = dateFormat.parse(cita.fecha)
        val time: Date = timeFormat.parse(cita.hora)

        return (System.currentTimeMillis() > date.time) && (System.currentTimeMillis() > time.time)
    }

}