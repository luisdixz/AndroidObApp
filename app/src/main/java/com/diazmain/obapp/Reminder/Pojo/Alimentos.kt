package com.diazmain.obapp.Reminder.Pojo

class Alimentos (_alimento: String, _porcion: String) {

    var alimento: String = _alimento
    get() = field
    set(value) {
        field = value
    }

    var porcion: String = _porcion
    get() = field
    set(value) {
        field = value
    }

    override fun toString(): String {
        return "Comida -> "+alimento+", Porción -> "+porcion+"\n"
    }

}