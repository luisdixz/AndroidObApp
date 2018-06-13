package com.diazmain.obapp.Login.helper

import android.content.Context
import android.content.SharedPreferences
import com.diazmain.obapp.Login.model.User

class SharedPrefManager (private val mCtx: Context) {

    companion object {
        private val SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit"

        private val KEY_USER_ID = "keyuserid"
        private val KEY_USER_NAME = "keyusername"
        private val KEY_USER_LASTNAME = "keyuserlastname"
        private val KEY_USER_USERNAME = "keyuserusername"

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

}