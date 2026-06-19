package com.sd.laborator.services

import com.sd.laborator.interfaces.ICryptoService
import com.sd.laborator.interfaces.IUserService
import com.sd.laborator.pojo.Expense
import com.sd.laborator.pojo.User
import com.sd.laborator.repositories.ExpenseRepository
import com.sd.laborator.repositories.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val cryptoService: ICryptoService
) : IUserService {

    override fun registerUser(username: String, parola: String, soldInitial: Double, nume: String, prenume: String): User {
        val user = User(username = username, password = cryptoService.hashPassword(username, parola),
                    nume = cryptoService.encrypt(nume), prenume = cryptoService.encrypt(prenume),sold = soldInitial )
        return userRepository.save(user)
    }


    override fun getBalance(username: String): Double = findByUsername(username)?.sold ?: 0.0

    override fun findByUsername(username: String): User? {
        val user = userRepository.findByUsername(username)
        return user?.copy(
                nume = cryptoService.decrypt(user.nume),
                prenume = cryptoService.decrypt(user.prenume)
        )
    }

    @Transactional
    override fun updateSold(username: String, sumaNoua: Double) {
        // Îl căutăm pe cel "viu" din baza de date
        val userDinDB = userRepository.findByUsername(username)
                ?: throw Exception("Utilizatorul $username nu există!")

        // Îi modificăm doar buzunarul.
        // userDinDB are deja ID, nume criptat, parolă hash-uită - nu pierdem nimic!
        userDinDB.sold = sumaNoua

        // Salvăm obiectul complet înapoi
        userRepository.save(userDinDB)
    }

    override fun login(username: String, parola: String): Boolean {
        val user = userRepository.findByUsername(username) ?: return false

        // Generăm hash-ul pentru parola introdusă acum ca să o comparăm cu cea stocată
        val hashIntrodus = cryptoService.hashPassword(username, parola)

        return user.password == hashIntrodus
    }
}