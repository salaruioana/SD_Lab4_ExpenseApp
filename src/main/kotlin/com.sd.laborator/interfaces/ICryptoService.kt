package com.sd.laborator.interfaces

interface ICryptoService {
    fun encrypt(value: String): String
    fun decrypt(encryptedValue: String): String
    fun hashPassword(username: String, parola: String): String
}