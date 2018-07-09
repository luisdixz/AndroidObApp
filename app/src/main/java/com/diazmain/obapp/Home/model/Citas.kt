package com.diazmain.obapp.Home.model

import com.google.gson.annotations.SerializedName

class Citas (_citas: List<CitasValue>) {

    @SerializedName("citas")
    var citas: List<CitasValue> = _citas
    get() = field
    set(value) {
        field = value
    }
}