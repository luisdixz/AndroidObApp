package com.diazmain.obapp.Home.model.meals

import com.google.gson.annotations.SerializedName

class OptionsPerMeal(_op1: List<OptionMeal>, _op2: List<OptionMeal>, _op3: List<OptionMeal>, _op4: List<OptionMeal>, _op5: List<OptionMeal>) {

    @SerializedName("opcion1")
    var op1: List<OptionMeal> = _op1
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("opcion2")
    var op2: List<OptionMeal> = _op2
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("opcion3")
    var op3: List<OptionMeal> = _op3
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("opcion4")
    var op4: List<OptionMeal> = _op4
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("opcion5")
    var op5: List<OptionMeal> = _op5
    get() = field
    set(value) {
        field = value
    }

}