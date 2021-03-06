package com.diazmain.obapp.reminder.pojo

class Alimentos(_alimento: String, _porcion: String, _cambiopor: String) {

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

    var cambiopor: String = _cambiopor
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "\n• " + alimento + ", " + porcion + "\nCambio por: " + cambiopor+"\n"
    }

}