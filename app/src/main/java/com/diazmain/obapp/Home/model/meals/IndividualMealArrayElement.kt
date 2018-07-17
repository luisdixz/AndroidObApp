package com.diazmain.obapp.Home.model.meals

import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.CheckBox
import android.widget.ImageButton

class IndividualMealArrayElement(_ibutton: ImageButton, _clay: ConstraintLayout, _cbox: CheckBox, _til: TextInputLayout, _tiet: TextInputEditText, _swap: ArrayList<String>) {

    var ibutton: ImageButton = _ibutton
        get() = field
        set(value) {
            field = value
        }

    var clay: ConstraintLayout = _clay
        get() = field
        set(value) {
            field = value
        }

    var cbox: CheckBox = _cbox
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

    var swap: ArrayList<String> = _swap
        get() = field
        set(value) {
            field = value
        }

}