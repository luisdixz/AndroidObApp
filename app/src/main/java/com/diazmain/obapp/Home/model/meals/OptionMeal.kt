package com.diazmain.obapp.Home.model.meals

import com.google.gson.annotations.SerializedName

class OptionMeal(_alimento: String, _porcion: String, _cambio: OptionMealSwitch) {

    @SerializedName("alimento")
    var alimento: String = _alimento
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("porcion")
    var porcion: String = _porcion
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("cambiopor")
    var cambio: OptionMealSwitch = _cambio
    get() = field
    set(value) {
        field = value
    }
}