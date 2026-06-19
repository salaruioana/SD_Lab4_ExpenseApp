package com.sd.laborator.controllers

import com.sd.laborator.interfaces.IExpenseService
import com.sd.laborator.interfaces.IUserService
import com.sd.laborator.pojo.ExpenseCategory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ViewController(
        private val userService: IUserService,
        private val expenseService: IExpenseService
) {
    @GetMapping("/")
    fun loginPage() = "login"

    @GetMapping("/register")
    fun registerPage() = "register"

    @GetMapping("/dashboard")
    fun dashboard(@RequestParam username: String, model: Model): String {
        val user = userService.findByUsername(username) ?: return "redirect:/"
        val expenses = expenseService.getUserHistory(username)

        model.addAttribute("user", user)
        model.addAttribute("expenses", expenses)
        model.addAttribute("categories", ExpenseCategory.values()) // Pentru dropdown
        return "dashboard"
    }
}