package com.diazmain.obapp.threads.local_queries

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.diazmain.obapp.room.DatabaseModule
import com.diazmain.obapp.room.UserEntity

class GetSpecificUser(_context: Context, _search: Int) : AsyncTask<Void, Void, UserEntity>() {

    @SuppressLint("StaticFieldLeak")
    private val mycontext: Context = _context
    private val search: Int = _search

    override fun doInBackground(vararg params: Void?): UserEntity? {
        val db = DatabaseModule().provideDatabase(mycontext)
        val dao = DatabaseModule().provideUserDao(db)
        if (search==0)
            return dao.findLast()
        else
            return dao.findById(search)
    }

    override fun onPostExecute(result: UserEntity?) {
        //Log.w("Location", "GetSpecificUser -> onPostExecute")
        Log.w("userEntity", result?.id.toString())
    }

}