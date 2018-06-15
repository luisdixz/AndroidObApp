package com.diazmain.obapp.Login.model

class User {

    private val id: Int
    private val nombre: String
    private val apellidos: String
    private val username: String
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