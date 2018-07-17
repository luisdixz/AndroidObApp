package com.diazmain.obapp.Home.model.meals

import com.google.gson.annotations.SerializedName

class MealTime(_desayuno:  OptionsPerMeal, _colacion1: OptionsPerMeal, _comida: OptionsPerMeal, _colacion2: OptionsPerMeal, _cena: OptionsPerMeal) {

    @SerializedName("desayuno")
    var desayuno: OptionsPerMeal = _desayuno
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("colacion1")
    var colacion1: OptionsPerMeal = _colacion1
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("comida")
    var comida: OptionsPerMeal = _comida
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("colacion2")
    var colacion2: OptionsPerMeal = _colacion2
        get() = field
        set(value) {
            field = value
        }

    @SerializedName("cena")
    var cena: OptionsPerMeal = _cena
        get() = field
        set(value) {
            field = value
        }

}