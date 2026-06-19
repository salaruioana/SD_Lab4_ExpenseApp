package com.sd.laborator.interfaces

import com.sd.laborator.pojo.User

interface IUserService {
    fun registerUser(username: String, parola: String, soldInitial: Double,nume: String, prenume: String): User
    fun getBalance(username: String): Double
    fun findByUsername(username: String): User?
    fun updateSold(username: String, sumaNoua: Double)
    fun login(username: String, parola: String):Boolean
}