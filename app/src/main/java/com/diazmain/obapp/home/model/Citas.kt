package com.diazmain.obapp.home.model

import com.google.gson.annotations.SerializedName

class Citas (_citas: CitasValue) {

    @SerializedName("citas")
    var citas: CitasValue = _citas
    get() = field
    set(value) {
        field = value
    }
}