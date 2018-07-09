package com.diazmain.obapp.Login.model

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("idPaciente")
    private val id: Int
    @SerializedName("nombre")
    private val nombre: String
    @SerializedName("apellidos")
    private val apellidos: String
    @SerializedName("username")
    private val username: String
    @SerializedName("password")
    private val password: String

    constructor(id: Int, name: String, lastname: String, username: String, password: String) {
        this.id = id
        this.nombre = name
        this.username = username
        this.apellidos = lastname
        this.password = password
    }

    constructor(name: String, lastname: String, username: String, password: String){
        this.id = 0
        this.nombre = name
        this.apellidos = lastname
        this.username = username
        this.password = password
    }

    constructor(id: Int, name: String, lastname: String, username: String) {
        this.id = id
        this.nombre = name
        this.apellidos = lastname
        this.username = username
        this.password = ""
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return nombre
    }

    fun getLastname(): String {
        return apellidos
    }

    fun getPassword(): String {
        return password
    }

    fun getUsername(): String {
        return username
    }

}