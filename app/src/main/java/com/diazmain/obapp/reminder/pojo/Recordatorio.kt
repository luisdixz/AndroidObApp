package com.diazmain.obapp.reminder.pojo

import android.arch.persistence.room.Embedded

class Recordatorio (idpaciente: Int, fecha: String, dia: Int, desayuno: Comidas, colacion1: Comidas, comida: Comidas, colacion2: Comidas, cena: Comidas){

    var idpaciente = idpaciente
        get() = field
        set(value) {field = value}

    var fecha = fecha
        get() = field
        set(value) {field = value}

    var dia = dia
        get() = field
        set(value) {field = value}

    @Embedded(prefix = "desayuno_")
    var desayuno = desayuno
        get() = field
        set(value) {field = value}

    @Embedded(prefix = "colacion1_")
    var colacion1 = colacion1
        get() = field
        set(value) {field = value}

    @Embedded(prefix = "comida_")
    var comida = comida
        get() = field
        set(value) {field = value}

    @Embedded(prefix = "colacion2_")
    var colacion2 = colacion2
        get() = field
        set(value) {field = value}

    @Embedded(prefix = "cena_")
    var cena = cena
        get() = field
        set(value) {field = value}
}