package com.diazmain.obapp.threads.local_queries

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import com.diazmain.obapp.R
import com.diazmain.obapp.home.fragments.ReminderSummaryFragment
import com.diazmain.obapp.room.DatabaseModule
import com.diazmain.obapp.room.ReminderEntity

class DisplayReminder(_context: Context, _date: String, _userid: Int) : AsyncTask<TextView, Void, ReminderEntity>() {

    @SuppressLint("StaticFieldLeak")
    val mycontext: Context = _context
    val date: String = _date
    val id: Int = _userid

    @SuppressLint("StaticFieldLeak")
    private lateinit var tvDes: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvCo1: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvCom: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvCo2: TextView
    @SuppressLint("StaticFieldLeak")
    private lateinit var tvCen: TextView

    override fun doInBackground(vararg params: TextView?): ReminderEntity? {
        Log.w("Location", "DisplayReminder -> doInBackground")
        Log.w("dateContent", date)

        tvDes = params[0] as TextView
        tvCo1 = params[1] as TextView
        tvCom = params[2] as TextView
        tvCo2 = params[3] as TextView
        tvCen = params[4] as TextView

        val db = DatabaseModule().provideDatabase(mycontext)
        val dao = DatabaseModule().provideReminderDao(db)
        //dao.nukeTable()
        return dao.findByDate(date, id.toLong())
    }

    override fun onPostExecute(result: ReminderEntity?) {
        val desayuno: String = result?.reminder?.desayuno.toString()
        val colacion1: String = result?.reminder?.colacion1.toString()
        val comida: String = result?.reminder?.comida.toString()
        val colacion2: String = result?.reminder?.colacion2.toString()
        val cena: String = result?.reminder?.cena.toString()

        Log.w("Location", "DisplayFragment -> onPostExecute")
        Log.w("desayunoContent" , desayuno)
        //Log.w("desayunoFecha", result?.fecha)

        result?.let {
            tvDes.setText(desayuno.replace("[", "").replace("]",""))
            tvCo1.setText(colacion1.replace("[", "").replace("]",""))
            tvCom.setText(comida.replace("[", "").replace("]",""))
            tvCo2.setText(colacion2.replace("[", "").replace("]",""))
            tvCen.setText(cena.replace("[", "").replace("]",""))
        } ?: run {
            tvDes.setText(mycontext.getString(R.string.label_prog_anuncio_default))
            tvCo1.setText(mycontext.getString(R.string.label_prog_anuncio_default))
            tvCom.setText(mycontext.getString(R.string.label_prog_anuncio_default))
            tvCo2.setText(mycontext.getString(R.string.label_prog_anuncio_default))
            tvCen.setText(mycontext.getString(R.string.label_prog_anuncio_default))
        }
    }
}