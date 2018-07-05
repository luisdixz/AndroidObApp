package com.diazmain.obapp.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.diazmain.obapp.Home.model.LastMeasures
import com.diazmain.obapp.Home.model.MeasuresValue
import com.diazmain.obapp.Login.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPrefManager (private val mCtx: Context) {

    companion object {
        private val SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit"

        private val KEY_USER_ID = "keyuserid"
        private val KEY_USER_NAME = "keyusername"
        private val KEY_USER_LASTNAME = "keyuserlastname"
        private val KEY_USER_USERNAME = "keyuserusername"

        private val KEY_MEASURES_HISTORY = "keymeasureshistory"

        private var mInstance: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager? {
            synchronized(this) {
                if (mInstance == null) {
                    mInstance = SharedPrefManager(context);
                }
            }

            return mInstance
        }
    }

    fun userLogin(user: User) : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.putInt(KEY_USER_ID, user.getId())
        editor.putString(KEY_USER_NAME, user.getName())
        editor.putString(KEY_USER_LASTNAME, user.getLastname())
        editor.putString(KEY_USER_USERNAME, user.getUsername())
        editor.apply()

        return true
    }

    fun isLoggedIn() : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        if (sPref.getString(KEY_USER_USERNAME, null) != null) {
            return true
        }

        return false
    }

    fun getUser() : User {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val user: User = User(
                sPref.getInt(KEY_USER_ID, 0),
                sPref.getString(KEY_USER_NAME, null),
                sPref.getString(KEY_USER_LASTNAME, null),
                sPref.getString(KEY_USER_USERNAME, null)
        )
        return user
    }

    fun logout() : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.clear()
        editor.apply()

        return true
    }




    fun storeAllMeasures(measures: List<MeasuresValue>) : Boolean {
        var res: Boolean = false

        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        val gson: Gson = Gson()
        val json = gson.toJson(measures)

        editor.putString(KEY_MEASURES_HISTORY, json)
        editor.apply()

        res = true

        return res
    }

    fun getAllMeasures() : List<MeasuresValue> {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val gson: Gson = Gson()
        var measures: List<MeasuresValue> = ArrayList()
        if (!isMeasuresArrayEmpty()) {
            Log.wtf("getAllMeasures -> KeyContent", sPref.getString(KEY_MEASURES_HISTORY, null))
            var response: String = sPref.getString(KEY_MEASURES_HISTORY,null)
            val type: Type = object: TypeToken<List<MeasuresValue>>(){}.type

            measures = gson.fromJson(response, type)

        }

        return measures
    }

    fun getLastMeasures() : LastMeasures{
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val gson: Gson = Gson()
        val measures: List<MeasuresValue> = this.getAllMeasures()

        val emptyMeasures: MeasuresValue = MeasuresValue()
        emptyMeasures.grasa = 0.0
        emptyMeasures.cintura = 0.0
        emptyMeasures.peso = 0.0

        when (measures.size) {
            0 -> { return LastMeasures(emptyMeasures, emptyMeasures , 0) }
            1 -> { return LastMeasures(emptyMeasures, measures.get(0) , 1) }
            else -> { return LastMeasures(measures.get(measures.size-2), measures.last(), 2) }
        }
    }

    fun isMeasuresArrayEmpty() : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        //val content: String = sPref.getString(KEY_MEASURES_HISTORY, null)
        if (sPref.getString(KEY_MEASURES_HISTORY, null)!=null) {
            Log.wtf("isArrayEmpty","false")
            return false
        }
        Log.wtf("isArrayEmpty","true")
        //Log.wtf("isMeasuresArrayEmpty -> KeyContent", sPref.getString(KEY_MEASURES_HISTORY, null))

        return true
    }

}