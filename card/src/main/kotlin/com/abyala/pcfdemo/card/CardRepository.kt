package com.abyala.pcfdemo.card

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.Month
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Repository
interface CardRepository : CrudRepository<Card, Int> {
    fun findByPan(pan: String): Card?
}

@Entity
class Card(
        @Id
        val pan: String,
        val expirationDate: LocalDate = randomExpirationDate()
) {

    companion object {
        fun randomExpirationDate(): LocalDate =
                LocalDate.of(2020, Month.JANUARY, 1).plusMonths(Random().nextInt(36).toLong())

    }
}