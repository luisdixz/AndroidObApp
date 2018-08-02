package com.diazmain.obapp.threads.local_queries

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.room.DatabaseModule
import com.diazmain.obapp.room.ReminderEntity

class ReminderFromToday(_context: Context, _date: String, _id: Int, _task: TaskCompleted) : AsyncTask<Void, Void, ReminderEntity>() {

    @SuppressLint("StaticFieldLeak")
    private val myContext: Context = _context
    private val date: String = _date
    private val idUser: Int = _id
    private val task: TaskCompleted = _task

    interface TaskCompleted {
        fun onTodayReminderNotRegistered(isStored: Boolean)
    }

    override fun doInBackground(vararg params: Void?): ReminderEntity? {
        val db = DatabaseModule().provideDatabase(myContext)
        val dao = DatabaseModule().provideReminderDao(db)
        return dao.findByDate(date, idUser.toLong())
    }


    override fun onPostExecute(result: ReminderEntity?) {
        Log.w("Location", "ReminderFromToday -> onPostExecute")
        /*if (result!!.equals(null)){
            Log.w("findByDate", "null")
            SharedPrefManager.getInstance(myContext)!!.cleanReminderParts()
        }
        else
            Log.w("findByDate", "not null")*/
        result?.let {
            Log.w("findByDate", "not null")
            task.onTodayReminderNotRegistered(false)
        }
        ?: run {
            Log.w("findByDate", "null")
            task.onTodayReminderNotRegistered(true)
        }
    }
}