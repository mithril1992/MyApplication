package com.example.myapplication.mvp.model

interface LoginDAO {
    fun login(userId: String, password: String): Boolean
}

object ConcreteLoginDAO: LoginDAO {
    override fun login(userId: String, password: String) = userId == "XXXX" && password == "4340"
}