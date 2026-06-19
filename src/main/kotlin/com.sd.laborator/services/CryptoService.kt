package com.sd.laborator.services

import com.sd.laborator.interfaces.ICryptoService
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Service
class CryptoService : ICryptoService {
    private val key = "1234567812345678" // Cheie de 16 caractere pentru AES-128
    private val algorithm = "AES"

    // Criptare Simetrică AES (GDPR)
    override fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(), algorithm))
        return Base64.getEncoder().encodeToString(cipher.doFinal(value.toByteArray()))
    }

    override fun decrypt(encryptedValue: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key.toByteArray(), algorithm))
        return String(cipher.doFinal(Base64.getDecoder().decode(encryptedValue)))
    }

    // Hashing pentru Parolă (SHA-256)
    override fun hashPassword(username: String, parola: String): String {
        val input = username + parola
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}