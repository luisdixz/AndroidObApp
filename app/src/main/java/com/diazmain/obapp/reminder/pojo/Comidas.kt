package com.diazmain.obapp.reminder.pojo

import android.arch.persistence.room.Embedded

class Comidas(hora: String?, alimentos: ArrayList<Alimentos>?, extras: String?) {

    var hora: String? = hora
        get() = field
        set(value) {
            field = value
        }

    var alimentos: ArrayList<Alimentos>? = alimentos
        get() = field
        set(value) {
            field = value
        }

    var extras: String? = extras
        get() = field
        set(value) {
            field = value
        }


    override fun toString(): String {
        //return "Hora -> " + hora + "alimentos [ " + alimentos.toString() + " ]\n"
        return "Hora: " + hora + alimentos.toString() + "\n\nExtras: \n• " + extras
    }
}