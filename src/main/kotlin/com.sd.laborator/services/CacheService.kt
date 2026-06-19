package com.sd.laborator.services

import com.sd.laborator.interfaces.ICacheService
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

@Service
class CacheService : ICacheService {

    // Wrapper-ul care ține datele și timestamp-ul
    private data class CacheEntry(
        val continut: Any,
        val timestampSalvare: Instant = Instant.now()
    )

    private val cacheStore = ConcurrentHashMap<String, CacheEntry>()
    private val expireMinutes = 30L

    override fun <T : Any> salveaza(cheie: String, continut: T) {
        cacheStore[cheie] = CacheEntry(continut)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> obtine(cheie: String): Optional<T> {
        val entry = cacheStore[cheie] ?: return Optional.empty()

        val acum = Instant.now()
        val minuteTrecute = Duration.between(entry.timestampSalvare, acum).toMinutes()

        return if (minuteTrecute < expireMinutes) {
            // Cererea a apărut în primele 30 de minute -> Valid!
            Optional.of(entry.continut as T)
        } else {
            // Au trecut cele 30 de minute -> Ștergem din memorie
            cacheStore.remove(cheie)
            Optional.empty()
        }
    }

    override fun sterge(cheie: String) {
        cacheStore.remove(cheie)
    }
}