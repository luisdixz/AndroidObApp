package com.diazmain.obapp.Reminder.Pojo

class Recordatorio (_idpaciente: Int, _fecha: String, _dia: Int, _desayuno: Comidas, _colacion1: Comidas, _comida: Comidas, _colacion2: Comidas, _cena: Comidas){

    var idpaciente = _idpaciente
        get() = field
        set(value) {field = value}

    var fecha = _fecha
        get() = field
        set(value) {field = value}

    var dia = _dia
        get() = field
        set(value) {field = value}

    var desayuno = _desayuno
        get() = field
        set(value) {field = value}

    var colacion1 = _colacion1
        get() = field
        set(value) {field = value}

    var comida = _comida
        get() = field
        set(value) {field = value}

    var colacion2 = _colacion2
        get() = field
        set(value) {field = value}

    var cena = _cena
        get() = field
        set(value) {field = value}
}