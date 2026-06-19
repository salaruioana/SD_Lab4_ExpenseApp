package com.sd.laborator.pojo

import javax.persistence.*

@Entity
@Table(name = "utilizatori")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val username: String = "",
        val password: String = "",
        val nume: String = "",
        val prenume: String = "",
        var sold: Double = 0.0
)