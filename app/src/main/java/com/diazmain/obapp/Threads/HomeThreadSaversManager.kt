package com.diazmain.obapp.Threads

import android.content.Context
import android.os.AsyncTask
import java.util.*

class HomeThreadSaversManager (_id: Int, _context: Context, _delegate: AsyncResponse) : AsyncTask<Any, Void, Boolean>() {

    public interface AsyncResponse {
        fun onProcessFinish(output: Boolean)
    }

    private val id: Int = _id
    private val context: Context = _context
    private val delegate: AsyncResponse = _delegate

    override fun doInBackground(vararg params: Any?): Boolean {
        val saveIncomingAppoint: SaveIncomingAppoint = SaveIncomingAppoint(id, context, object : SaveIncomingAppoint.AsyncResponse {
            override fun onProcessFinish(output: Boolean) {

            }
        })
        val saveRecentsMeasures: SaveRecentsMeasures = SaveRecentsMeasures(id, context, object : SaveRecentsMeasures.AsyncResponse {
            override fun onProcessFinish(output: Boolean) {

            }

        })

        saveIncomingAppoint.execute()
        saveRecentsMeasures.execute()

        return true
    }

    override fun onPostExecute(result: Boolean) {
        delegate.onProcessFinish(result)
    }

}