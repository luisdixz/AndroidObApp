package com.diazmain.obapp.threads

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.diazmain.obapp.home.model.CitasValue
import com.diazmain.obapp.notification.JobsManager
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

        cita = SharedPrefManager.getInstance(context)?.getAppoint()!!
        return cita
    }

    override fun onPostExecute(result: CitasValue) {
        when (result.status) {
            0 -> {
                llNextAppo.visibility = View.GONE
                tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))

                JobsManager(context).scheduleJob(0)
            }
            1 -> {
                if (isApponitOutdate(cita)) {
                    tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))
                    JobsManager(context).scheduleJob(1)
                } else {
                    llNextAppo.visibility = View.VISIBLE
                    val date: String = cita.fecha +" - a las "+cita.hora+" hrs"
                    tvAppoDate.setText(date)

                    JobsManager(context).scheduleJob(1)
                }
            }
            2 -> {
                llNextAppo.visibility = View.GONE
                if (isApponitOutdate(cita)) {
                    tvAppoDate.setText(context.getString(R.string.label_next_appointment_default))
                    JobsManager(context).scheduleJob(2)
                } else {
                    val date: String = cita.fecha +" - a las "+cita.hora+" hrs"
                    tvAppoDate.setText(date +"\nCompleta el formulario de 24hrs antes de tu cita ")
                    JobsManager(context).scheduleJob(0)

                }
            }
        }
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

    fun getHour(cita: CitasValue) : Int {
        val hourFormat = SimpleDateFormat("HH")
        return Integer.parseInt(hourFormat.parse(cita.hora).toString())
    }

    fun getMin(cita: CitasValue) : Int {
        val minFormat = SimpleDateFormat("mm")
        return Integer.parseInt(minFormat.parse(cita.hora).toString())
    }
}