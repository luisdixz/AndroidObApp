package com.diazmain.obapp.threads.local_queries

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.room.DatabaseModule
import com.diazmain.obapp.room.UserEntity

class InsertNewUser(_context: Context, _user: UserEntity) : AsyncTask<Void, Void, Int>() {

    @SuppressLint("StaticFieldLeak")
    private val mycontext: Context = _context
    private val user: UserEntity = _user

    override fun doInBackground(vararg params: Void?): Int? {
        val db = DatabaseModule().provideDatabase(mycontext)
        val dao = DatabaseModule().provideUserDao(db)
        dao.insert(user)
        return dao.findLast()?.id
    }

    override fun onPostExecute(result: Int?) {

        Log.w("id", result.toString())
    }
}