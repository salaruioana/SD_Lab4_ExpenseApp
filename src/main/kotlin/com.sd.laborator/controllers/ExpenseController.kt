package com.sd.laborator.controllers

import com.sd.laborator.pojo.Expense
import com.sd.laborator.interfaces.IExpenseService
import com.sd.laborator.pojo.ExpenseCategory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/expenses")
class ExpenseController(private val expenseService: IExpenseService) {

    // adaugarea unei cheltuieli
    // Test: POST http://localhost:8080/api/expenses/add?username=ion&descriere=Paine&suma=5.5
    @PostMapping("/add")
    fun addExpense(
            @RequestParam username: String,
            @RequestParam descriere: String,
            @RequestParam suma: Double,
            @RequestParam categorie: ExpenseCategory
    ): Any {
        return try {
            expenseService.addExpense(username, descriere, suma,categorie);
        } catch (e: Exception) {
            mapOf("eroare" to e.message)
        }
    }

    // vezi istoricul unui user
    // Test: GET http://localhost:8080/api/expenses/history/ion
    @GetMapping("/history/{username}")
    fun getHistory(@PathVariable username: String): List<Expense> {
        return expenseService.getUserHistory(username)
    }
    @PostMapping("/add-ui")
    fun addExpenseUI(
            @RequestParam username: String,
            @RequestParam descriere: String,
            @RequestParam suma: Double,
            @RequestParam categorie: ExpenseCategory
    ): String {
        expenseService.addExpense(username, descriere, suma, categorie)
        return "redirect:/dashboard?username=$username"
    }
}