package com.diazmain.obapp.room.converters

import android.arch.persistence.room.TypeConverter
import com.diazmain.obapp.reminder.pojo.Alimentos
import com.diazmain.obapp.reminder.pojo.Comidas
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlimentosConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<Alimentos> {
        val listType = object : TypeToken<ArrayList<Alimentos>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Alimentos>) : String {
        val json: String = Gson().toJson(list)
        return json
    }
}