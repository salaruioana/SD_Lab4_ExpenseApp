package com.sd.laborator.repositories

import com.sd.laborator.pojo.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//Cine face implementarea? Tu scrii doar interfața care extinde JpaRepository. La pornirea aplicației, Spring Data JPA scanează aceste interfețe și generează automat în memorie (prin Proxy) implementarea lor (codul SQL pentru save, findAll, delete, etc.).
@Repository
interface UserRepository : JpaRepository<User, Long> {

    // query derivat din numele metodei
    //Query-uri derivate: Când scrii findByUsername(username: String), Spring parsează numele metodei și generează automat interogarea: SELECT * FROM utilizatori WHERE username = ?.
    fun findByUsername(username: String): User?
}