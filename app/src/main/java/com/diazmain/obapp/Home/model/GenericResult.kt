package com.diazmain.obapp.Home.model

import com.google.gson.annotations.SerializedName

class GenericResult(_error: Boolean, _message: String, _isStatusChanging: Boolean) {

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
    var isStatusChanging: Boolean = _isStatusChanging
    get() = field
    set(value) {
        field = value
    }

}