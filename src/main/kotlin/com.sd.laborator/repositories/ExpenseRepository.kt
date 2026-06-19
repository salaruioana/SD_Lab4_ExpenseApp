package com.sd.laborator.repositories

import com.sd.laborator.pojo.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExpenseRepository : JpaRepository<Expense, Long> {

    // Spring cauta în tabelul 'cheltuieli' coloana 'user_id' care corespunde cu id-ul primit
    fun findByUserId(userId: Long): List<Expense>
}