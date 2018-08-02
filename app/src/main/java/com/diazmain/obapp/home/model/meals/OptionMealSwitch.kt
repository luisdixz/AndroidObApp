package com.diazmain.obapp.home.model.meals

import com.google.gson.annotations.SerializedName

class OptionMealSwitch (_cambioUno: List<String>, _cambioDos: List<String>, _cambioTres: List<String>) {

    @SerializedName("uno")
    var cambioUno: List<String> = _cambioUno
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("dos")
    var cambioDos: List<String> = _cambioDos
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("tres")
    var cambioTres: List<String> = _cambioTres
        get() = field
        set(value) {
            field = value
        }

}