package com.sd.laborator.controllers

import com.sd.laborator.interfaces.IExpenseService
import com.sd.laborator.interfaces.ICacheService
import com.sd.laborator.interfaces.IUserService
import com.sd.laborator.pojo.ExpenseCategory
import org.springframework.stereotype.Controller
import com.sd.laborator.pojo.Expense
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ViewController(
        private val userService: IUserService,
        private val expenseService: IExpenseService,
        private val cacheService: ICacheService
) {
    @GetMapping("/")
    fun loginPage() = "login"

    @GetMapping("/register")
    fun registerPage() = "register"

    @GetMapping("/dashboard")
    fun dashboard(@RequestParam username: String, model: Model): String {
        val user = userService.findByUsername(username) ?: return "redirect:/"

        val cacheKey = "expenses_history_$username"
        val cachedExpenses = cacheService.obtine<List<Expense>>(cacheKey)
        val expenses = if (cachedExpenses.isPresent) {
            // HIT: Suntem în primele 30 de minute, luăm datele direct din cache
            println("[CACHE] HIT pentru userul $username. Returnez datele salvate.")
            cachedExpenses.get()
        } else {
            // MISS: Nu sunt în cache sau au expirat cele 30 de min. Le luăm din serviciu...
            println("[CACHE] MISS pentru userul $username. Apelezi serviciul greu.")
            val freshExpenses = expenseService.getUserHistory(username)

            // ... și le salvăm în cache pentru următoarele 30 de minute
            cacheService.salveaza(cacheKey, freshExpenses)

            freshExpenses
        }



        model.addAttribute("user", user)
        model.addAttribute("expenses", expenses)
        model.addAttribute("categories", ExpenseCategory.values()) // Pentru dropdown
        return "dashboard"
    }
}