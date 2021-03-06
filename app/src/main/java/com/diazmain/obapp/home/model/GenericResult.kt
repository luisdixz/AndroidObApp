package com.diazmain.obapp.home.model

import com.google.gson.annotations.SerializedName

class GenericResult(_error: Boolean, _message: String, _isStatusChanging: Int) {

    @SerializedName("error")
    var error: Boolean = _error
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("message")
    var message: String = _message
    get() = field
    set(value) {
        field = value
    }

    @SerializedName("affectedRows")
    var isStatusChanging: Int = _isStatusChanging
    get() = field
    set(value) {
        field = value
    }

}