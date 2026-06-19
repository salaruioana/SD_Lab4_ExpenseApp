package com.sd.laborator.pojo

import com.sd.laborator.pojo.User
import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cheltuieli")
data class Expense(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val descriere: String = "",
        val suma: Double = 0.0,

        @Enumerated(EnumType.STRING) // Salveaza numele (ex: "MANCARE") in baza de date
        val categorie: ExpenseCategory = ExpenseCategory.PERSONALE,

        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User? = null
)
