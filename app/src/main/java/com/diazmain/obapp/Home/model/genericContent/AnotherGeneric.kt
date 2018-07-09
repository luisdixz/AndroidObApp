package com.diazmain.obapp.Home.model.genericContent

import com.google.gson.annotations.SerializedName


class AnotherGeneric(_executing : Boolean, _numRows: Int) {

    @SerializedName("executing?")
    val executing: Boolean = _executing

    @SerializedName("numRows")
    val numRows: Int = _numRows

}
