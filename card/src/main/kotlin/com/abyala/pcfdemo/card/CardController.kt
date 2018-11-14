package com.abyala.pcfdemo.card

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class CardController(private val cardRepository: CardRepository) {
    private val log = LoggerFactory.getLogger(CardController::class.java)

    @GetMapping("/cards")
    fun findAllCards() =
            cardRepository.findAll().map {
                CardDto(it.pan, it.expirationDate)
            }

    @GetMapping("/cards/{pan}")
    fun findCard(@PathVariable("pan") pan: String): CardDto {
        log.info("Searching for pan $pan. Don't tell Infosec!")
        return cardRepository.findByPan(pan)?.toDto() ?: throw CardNotFoundException()
    }

    @PostMapping("/cards/{pan}")
    fun createCard(@PathVariable("pan") pan: String) {
        log.info("Creating card with pan $pan")
        cardRepository.save(Card(pan))
    }

    @ExceptionHandler(CardNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCardNotFoundException() = ErrorResponse("Card not found")

    private fun Card.toDto() = CardDto(this.pan, this.expirationDate)
}

class CardDto(val pan: String, val expirationDate: LocalDate)
class ErrorResponse(val message: String)
class CardNotFoundException : Exception()