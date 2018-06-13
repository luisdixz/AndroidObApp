package com.diazmain.obapp.Login.model

class User {

    private val id: Int
    private val name: String
    private val lastname: String
    private val username: String
    private val password: String

    constructor(id: Int, name: String, lastname: String, username: String, password: String) {
        this.id = id
        this.name = name
        this.username = username
        this.lastname = lastname
        this.password = password
    }

    constructor(name: String, lastname: String, username: String, password: String){
        this.id = 0
        this.name = name
        this.lastname = lastname
        this.username = username
        this.password = password
    }

    constructor(id: Int, name: String, lastname: String, username: String) {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.username = username
        this.password = ""
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getLastname(): String {
        return lastname
    }

    fun getPassword(): String {
        return password
    }

    fun getUsername(): String {
        return username
    }

}