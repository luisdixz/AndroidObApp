package com.diazmain.obapp.login.model

import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("error")
    private val error: Boolean

    @SerializedName("message")
    private val message: String

    @SerializedName("user")
    private val user: User

    constructor(error: Boolean, message: String, user: User) {
        this.error = error
        this.message = message
        this.user = user
    }

    fun getError() : Boolean {
        return error
    }

    fun getMessage() : String {
        return message
    }

    fun getUser() : User {
        return user
    }
}