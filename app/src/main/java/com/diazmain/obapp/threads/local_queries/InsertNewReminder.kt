package com.diazmain.obapp.threads.local_queries

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.diazmain.obapp.room.DatabaseModule
import com.diazmain.obapp.room.ReminderEntity

class InsertNewReminder(_context: Context, _rem: ReminderEntity) : AsyncTask<Void, Void, Long>() {

    val myContext: Context = _context
    val reminder: ReminderEntity = _rem



    override fun doInBackground(vararg params: Void?): Long {
        val db = DatabaseModule().provideDatabase(myContext)
        val dao = DatabaseModule().provideReminderDao(db)
        //dao.nukeTable()
        return dao.insert(reminder)
    }

    override fun onPostExecute(result: Long?) {
        Log.w("Location", "InsertNewReminder -> onPostExecute")
        Log.w("rows affected", result.toString())
        RowsCount(myContext, "reminders").execute()
        Toast.makeText(myContext, "Recordatorio guardado", Toast.LENGTH_LONG).show()
    }
}