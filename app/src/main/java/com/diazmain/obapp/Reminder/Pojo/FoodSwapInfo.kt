package com.diazmain.obapp.Reminder.Pojo

class FoodSwapInfo(_pagina: Int, _op: Int, _food: String, _swap1: String, _swap2: String, _swap3: String) {

    var pagina: Int = _pagina
        get() = field
        set(value) {
            field = value
        }

    var op: Int = _op
        get() = field
        set(value) {
            field = value
        }

    var food: String = _food
        get() = field
        set(value) {
            field = value
        }

    var swap1: String = _swap1
        get() = field
        set(value) {
            field = value
        }

    var swap2: String = _swap2
        get() = field
        set(value) {
            field = value
        }

    var swap3: String = _swap3
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Pagina: " + pagina + " - Food: " + food + " - Swap1: " + swap1
    }

}