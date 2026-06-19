package com.sd.laborator.services

import com.sd.laborator.interfaces.IExpenseService
import com.sd.laborator.interfaces.IUserService
import com.sd.laborator.pojo.Expense
import com.sd.laborator.pojo.ExpenseCategory
import com.sd.laborator.repositories.ExpenseRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ExpenseService(
        private val expenseRepository: ExpenseRepository,
        private val userService: IUserService
) : IExpenseService {

    @Transactional
    override fun addExpense(username: String, descriere: String, suma: Double, categorie: ExpenseCategory): Expense {
        val user = userService.findByUsername(username) ?: throw Exception("User negasit")

        if (user.sold < suma) throw Exception("Bani putini puiu..")

        val soldNou = user.sold - suma
        // Scadem banii prin serviciul de user
        userService.updateSold(username, soldNou)

        // Salvam cheltuiala
        return expenseRepository.save(Expense(descriere = descriere, suma = suma,categorie=categorie, user = user))
    }

    override fun getUserHistory(username: String): List<Expense> {
        val user = userService.findByUsername(username) ?: return emptyList()
        return expenseRepository.findByUserId(user.id!!)
    }
}