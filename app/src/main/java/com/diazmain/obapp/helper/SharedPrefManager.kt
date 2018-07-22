package com.diazmain.obapp.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.diazmain.obapp.Home.model.CitasValue
import com.diazmain.obapp.Home.model.LastMeasures
import com.diazmain.obapp.Home.model.MeasuresValue
import com.diazmain.obapp.Home.model.meals.MealMenuResult
import com.diazmain.obapp.Login.model.User
import com.diazmain.obapp.Reminder.Pojo.Comidas
import com.diazmain.obapp.Reminder.Pojo.Recordatorio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.math.BigDecimal

class SharedPrefManager (private val mCtx: Context) {

    companion object {
        private val SHARED_PREF_NAME = "simplifiedcodingsharedprefretrofit"

        private val KEY_USER_ID = "keyuserid"
        private val KEY_USER_NAME = "keyusername"
        private val KEY_USER_LASTNAME = "keyuserlastname"
        private val KEY_USER_USERNAME = "keyuserusername"
        private val KEY_USER_BIRTH = "keyuserbirth"

        private val KEY_MEASURES_HISTORY = "keymeasureshistory"

        private val KEY_APPOINT_ID = "keyappointid"
        private val KEY_APPOINT_TIPO = "keytipoappoint"
        private val KEY_APPOINT_HORA = "keyappointhora"
        private val KEY_APPOINT_FECHA = "keyappointfecha"
        private val KEY_APPOINT_STATUS = "keyappointstatus"

        private val KEY_MEAL_MENU = "keymealmenu"

        private val KEY_BREAKFAST_MEAL_TIME = "keybreakfastmealtime"
        private val KEY_FCOLLATION_MEAL_TIME = "keyfcollationmealtime"
        private val KEY_MEAL_MEAL_TIME = "keymealmealtime"
        private val KEY_SCOLLATION_MEAL_TIME = "keyscollationmealtime"
        private val KEY_DINNER_MEAL_TIME = "keydinnermealtime"

        private val KEY_LAST_REMINDER = "keylastreminder"
        private val KEY_LAST_REMINDER_DATE = "keylastreminderdate"

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

    private fun initKeys() {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.putString(KEY_BREAKFAST_MEAL_TIME,"")
        editor.putString(KEY_FCOLLATION_MEAL_TIME,"")
        editor.putString(KEY_MEAL_MEAL_TIME,"")
        editor.putString(KEY_SCOLLATION_MEAL_TIME,"")
        editor.putString(KEY_DINNER_MEAL_TIME,"")
    }

    fun userLogin(user: User) : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.putInt(KEY_USER_ID, user.getId())
        editor.putString(KEY_USER_NAME, user.getName())
        editor.putString(KEY_USER_LASTNAME, user.getLastname())
        editor.putString(KEY_USER_USERNAME, user.getUsername())
        editor.putString(KEY_USER_BIRTH, user.getBirth())
        editor.apply()

        initKeys()

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

        //Log.w("savedUserID", sPref.getInt(KEY_USER_ID, 0).toString())

        val user: User = User(
                sPref.getInt(KEY_USER_ID, 0),
                sPref.getString(KEY_USER_NAME, ""),
                sPref.getString(KEY_USER_LASTNAME, ""),
                sPref.getString(KEY_USER_USERNAME, ""),
                sPref.getString(KEY_USER_BIRTH, "")
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

        val measures: List<MeasuresValue> = this.getAllMeasures()

        val emptyMeasures: MeasuresValue = MeasuresValue()
        emptyMeasures.grasa = BigDecimal(0.0)
        emptyMeasures.cintura = BigDecimal(0.0)
        emptyMeasures.peso = BigDecimal(0.0)

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
            //Log.wtf("isArrayEmpty","false")
            return false
        }
        //Log.wtf("isArrayEmpty","true")
        //Log.wtf("isMeasuresArrayEmpty -> KeyContent", sPref.getString(KEY_MEASURES_HISTORY, null))

        return true
    }

    fun storeAppoint(cita: CitasValue) : Boolean {
        try {
            val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sPref.edit()

            if (sPref.getInt(KEY_APPOINT_STATUS, 0) != 1) {
                editor.putInt(KEY_APPOINT_ID, cita.idCita)
                editor.putString(KEY_APPOINT_TIPO, cita.tipoCita)
                editor.putString(KEY_APPOINT_HORA, cita.hora)
                editor.putString(KEY_APPOINT_FECHA, cita.fecha)
                editor.putInt(KEY_APPOINT_STATUS, cita.status)
            }
            editor.apply()

            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getAppoint() : CitasValue {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

       // if (isAppointAvaliable()){
            return CitasValue(
                    sPref.getInt(KEY_APPOINT_ID, 0),
                    sPref.getString(KEY_APPOINT_TIPO, ""),
                    sPref.getString(KEY_APPOINT_FECHA, ""),
                    sPref.getString(KEY_APPOINT_HORA, ""),
                    sPref.getInt(KEY_APPOINT_STATUS, 0)
            )
         /*
        } else {

        }*/
    }

    fun isAppointAvaliable() : Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        if (sPref.getInt(KEY_APPOINT_STATUS, 0) == 0)
            return false
        else if (sPref.getString(KEY_APPOINT_TIPO, null) == null)
            return false

        return true
    }

    fun setAppointStatus(newStatus: Int) {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.putInt(KEY_APPOINT_STATUS, newStatus)
        editor.apply()
    }

    fun getAppointStatus() : Int {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return sPref.getInt(KEY_APPOINT_STATUS, 0)
    }

    fun storeMealsMenu(menu: MealMenuResult) {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        val gson: Gson = Gson()
        val json: String = gson.toJson(menu)

        editor.putString(KEY_MEAL_MENU, json)
        editor.apply()

    }

    fun isMenuStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return !sPref.getString(KEY_MEAL_MENU, "").equals("")
    }

    fun getMealsMenu(): MealMenuResult {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val gson: Gson = Gson()
        val result: String = sPref.getString(KEY_MEAL_MENU, "")

        return gson.fromJson(result, MealMenuResult::class.java)
    }

    fun storeReminderPart(mealTime: Int, food: Comidas) {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        val gson: Gson = Gson()
        val json: String = gson.toJson(food)
        when (mealTime) {
            1 -> { editor.putString(KEY_BREAKFAST_MEAL_TIME, json)
            Log.w("storeReminderPart", "breakfast -> "+json)}
            2 -> { editor.putString(KEY_FCOLLATION_MEAL_TIME, json) }
            3 -> { editor.putString(KEY_MEAL_MEAL_TIME, json) }
            4 -> { editor.putString(KEY_SCOLLATION_MEAL_TIME, json) }
            5 -> { editor.putString(KEY_DINNER_MEAL_TIME, json) }
        }
        editor.apply()
    }

    fun getReminderPart(mealTime: Int) : Comidas {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val gson: Gson = Gson()
        var result: String = ""
        when (mealTime) {
            1 -> { result = sPref.getString(KEY_BREAKFAST_MEAL_TIME, "") }
            2 -> { result = sPref.getString(KEY_FCOLLATION_MEAL_TIME, "") }
            3 -> { result = sPref.getString(KEY_MEAL_MEAL_TIME, "") }
            4 -> { result = sPref.getString(KEY_SCOLLATION_MEAL_TIME, "") }
            5 -> { result = sPref.getString(KEY_DINNER_MEAL_TIME, "") }
        }
        return gson.fromJson(result, Comidas::class.java)
    }

    fun isBreakfastStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        Log.w("isBreakfastStored", "Breakfast -> "+sPref.getString(KEY_BREAKFAST_MEAL_TIME, ""))
        return !sPref.getString(KEY_BREAKFAST_MEAL_TIME, "").equals("")
    }

    fun isFirstCollationStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return !sPref.getString(KEY_FCOLLATION_MEAL_TIME, "").equals("")
    }

    fun isMealTimeStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return !sPref.getString(KEY_MEAL_MEAL_TIME, "").equals("")
    }

    fun isSecondCollationStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return !sPref.getString(KEY_SCOLLATION_MEAL_TIME, "").equals("")
    }

    fun isDinnerStored(): Boolean {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return !sPref.getString(KEY_DINNER_MEAL_TIME, "").equals("")
    }

    fun storeLastCompleteReminder(reminder: Recordatorio) {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        val reminderDate = reminder.fecha
        val gson: Gson = Gson()
        val json: String = gson.toJson(reminder)

        editor.putString(KEY_LAST_REMINDER, json)
        editor.putString(KEY_LAST_REMINDER_DATE, reminderDate)

        editor.apply()
    }

    fun getLastCompleteReminder() : Recordatorio {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val gson: Gson = Gson()
        val result: String = sPref.getString(KEY_LAST_REMINDER, "")
        return gson.fromJson(result, Recordatorio::class.java)
    }

    fun getLastCompleteReminderDate() : String {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

        return sPref.getString(KEY_LAST_REMINDER_DATE, "")
    }

    fun cleanReminderParts() {
        val sPref: SharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPref.edit()

        editor.putString(KEY_BREAKFAST_MEAL_TIME, "")
        editor.putString(KEY_FCOLLATION_MEAL_TIME, "")
        editor.putString(KEY_MEAL_MEAL_TIME, "")
        editor.putString(KEY_SCOLLATION_MEAL_TIME, "")
        editor.putString(KEY_DINNER_MEAL_TIME, "")

        editor.apply()
    }
}