package com.diazmain.obapp.threads

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.util.*
import kotlin.collections.ArrayList

class MarkEventsOnCalendar(_context: Context, _calendar: CompactCalendarView) : AsyncTask<String, Void, List<List<Event>>>()  {

    @SuppressLint("StaticFieldLeak")
    private val context = _context
    @SuppressLint("StaticFieldLeak")
    private val calendar = _calendar

    override fun doInBackground(vararg params: String): List<List<Event>> {
        val eventGeneral: ArrayList<List<Event>> = ArrayList()
        try {

            // Separar fecha
            val fecha = params[0].split("-")
            val año = Integer.parseInt(fecha[0])
            val mes = Integer.parseInt(fecha[1])
            val dia = Integer.parseInt(fecha[2])

            //Separar hora
            val tiempo = params[1].split(":")
            val hora = Integer.parseInt(tiempo[0])
            val minuto = Integer.parseInt(tiempo[1])

            val currentCalender = Calendar.getInstance(Locale.getDefault())
            currentCalender.time = Date()
            currentCalender.set(Calendar.DAY_OF_MONTH, dia)

            val events: ArrayList<Event> = ArrayList()

            currentCalender.set(Calendar.MONTH, mes - 1)
            currentCalender.set(Calendar.ERA, GregorianCalendar.AD)
            currentCalender.set(Calendar.YEAR, año)
            currentCalender.set(Calendar.HOUR_OF_DAY, hora)
            currentCalender.set(Calendar.MINUTE, minuto)
            currentCalender.set(Calendar.SECOND, 0)
            currentCalender.set(Calendar.MILLISECOND, 0)
            currentCalender.add(Calendar.DATE, 0)

            var timeInMillis = currentCalender.timeInMillis
            events.add(getEvents(timeInMillis, 0))
            eventGeneral.add(events)

            val events1: ArrayList<Event> = ArrayList()
            currentCalender.set(Calendar.DAY_OF_MONTH, dia - 1)
            timeInMillis = currentCalender.timeInMillis
            events1.add(getEvents(timeInMillis, 1))
            //publishProgress(events1)
            eventGeneral.add(events1)

            val events2: ArrayList<Event> = ArrayList()
            currentCalender.set(Calendar.DAY_OF_MONTH, dia - 2)
            timeInMillis = currentCalender.timeInMillis
            events2.add(getEvents(timeInMillis, 1))
            //publishProgress(events2)
            eventGeneral.add(events2)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return eventGeneral
    }

    /*override fun onProgressUpdate(vararg values: List<Event>?) {
        super.onProgressUpdate(*values)
        calendar.addEvents(values[0])
    }*/

    override fun onPostExecute(result: List<List<Event>>) {
        super.onPostExecute(result)
        try {
            calendar.removeAllEvents()
            calendar.addEvents(result.get(0))
            calendar.addEvents(result.get(1))
            calendar.addEvents(result.get(2))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getEvents(timeInMillis: Long, day: Int): Event {
        if (day == 0)
            return Event(Color.rgb(202,255,202), timeInMillis, "Appoint at " + Date(timeInMillis))
        else
            return Event(Color.rgb(117,240,161), timeInMillis, "Reminder at " + Date(timeInMillis))
    }
}