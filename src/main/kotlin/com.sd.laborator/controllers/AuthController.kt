package com.sd.laborator.controllers
import com.sd.laborator.interfaces.IUserService
import org.springframework.web.bind.annotation.*


// controller rest care gestioneaza cererile de postare a unui cont nou
@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val userService: IUserService
) {
    @PostMapping("/register")
    fun register(
            @RequestParam username: String,
            @RequestParam parola: String,
            @RequestParam nume: String,
            @RequestParam prenume: String,
            @RequestParam soldInitial: Double
    ): Any {
        // preia de la utilizator acei parametri si ii trimite serviciului
        return userService.registerUser(username, parola, soldInitial,nume,prenume)
    }
    @PostMapping("/login")
    fun login(@RequestParam username: String, @RequestParam parola: String): Any {
        val esteValid = userService.login(username, parola)

        return if (esteValid) {
            mapOf("status" to "success", "username" to username)
        } else {
            // Trimitem o eroare 401 (Neautorizat) dacă parola e greșită
            org.springframework.http.ResponseEntity
                    .status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                    .body(mapOf("status" to "error", "message" to "Date incorecte!"))
        }
    }

    @GetMapping("/balance/{username}")
    fun getBalance(@PathVariable username: String): Double {
        return userService.getBalance(username)
    }
}