package com.diazmain.obapp.Reminder.Pojo

class Comidas (_hora: Int, _alimentos: ArrayList<Alimentos>){

    var hora: Int = _hora
    get() = field
    set(value) {field = value}

    var alimentos: ArrayList<Alimentos> = _alimentos
    get() = field
    set(value) {field = value}


    override fun toString(): String {
        return "Hora -> "+ hora + "alimentos [ "+alimentos.toString()+" ]\n"
    }
}