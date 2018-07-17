package com.diazmain.obapp.Home.model.meals

import com.google.gson.annotations.SerializedName

class MealMenuResult (_menuCal: MealTime) {

    @SerializedName(value = "1300", alternate = arrayOf("1400","1500","1600","1700"))
    var menuCal: MealTime = _menuCal
    get() = field
    set(value) {
        field = value
    }

}