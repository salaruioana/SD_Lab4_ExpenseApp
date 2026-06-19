package com.sd.laborator.interfaces

import com.sd.laborator.pojo.Expense
import com.sd.laborator.pojo.ExpenseCategory

interface IExpenseService {
    fun addExpense(username: String, descriere: String, suma: Double, categorie: ExpenseCategory): Expense
    fun getUserHistory(username: String): List<Expense>
}