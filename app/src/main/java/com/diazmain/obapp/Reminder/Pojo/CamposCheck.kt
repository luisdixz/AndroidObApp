package com.diazmain.obapp.Reminder.Pojo

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.CheckBox

class CamposCheck(_pagina: Int, _comida: CheckBox, _til: TextInputLayout, _tiet: TextInputEditText) {

    var pagina: Int = _pagina
        get() = field
        set(value) {
            field = value
        }

    var comida: CheckBox = _comida
        get() = field
        set(value) {
            field = value
        }

    var til: TextInputLayout = _til
        get() = field
        set(value) {
            field = value
        }

    var tiet: TextInputEditText = _tiet
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Página -> " + pagina + ", Comida -> " + comida + "\n"
    }
}