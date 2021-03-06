package com.diazmain.obapp.home.model

import com.google.gson.annotations.SerializedName

class CitasValue (_idCita: Int, _tipoCita: String, _fecha: String, _hora: String, _status: Int) {

    @SerializedName("idCita")
    var idCita: Int = _idCita
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("tipoCita")
    var tipoCita: String = _tipoCita
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("fecha")
    var fecha: String = _fecha
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("hora")
    var hora: String = _hora
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("status")
    var status: Int = _status
    get() = field
    set(value) {
        field = value
    }

    override fun toString(): String {
        //return "idCita: "+idCita+"\ntipoCita: "+tipoCita+"\nfecha: "+ fecha+"\nhora: "+hora+"\nstatus: "+status
        return tipoCita+"\n"+ fecha+"\n"+hora
    }

}