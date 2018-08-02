package com.diazmain.obapp.threads.local_queries

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.room.DatabaseModule

class RowsCount(_context: Context, _table: String) : AsyncTask<Void, Void, Long>() {

    private val myContext: Context = _context
    private val table: String = _table

    override fun doInBackground(vararg params: Void?): Long {
        val db = DatabaseModule().provideDatabase(myContext)
        var res: Long
        if (table.equals("users")) {
            val dao = DatabaseModule().provideUserDao(db)
            res = dao.getRows()
        } else {
            val dao = DatabaseModule().provideReminderDao(db)
            res = dao.getRows()
        }
        return res
    }

    override fun onPostExecute(result: Long?) {
        Log.w("Location", "RowsCount -> onPostExecute")
        Log.w("rows", result.toString())
    }
}