package com.sd.laborator.interfaces

import java.util.*

interface ICacheService {
    fun <T : Any> salveaza(cheie: String, continut: T)
    fun <T : Any> obtine(cheie: String): Optional<T>
    fun sterge(cheie: String)
}