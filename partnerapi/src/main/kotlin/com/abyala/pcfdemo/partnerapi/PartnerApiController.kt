package com.abyala.pcfdemo.partnerapi

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RestController
class PartnerApiController(private val cardLookupService: CardLookupService) {

    @PostMapping("/order")
    fun orderCard(@RequestBody cardOrderRequest: CardOrderRequest): CardOrderDto {
        println("Looking for card ${cardOrderRequest.pan}")
        return cardLookupService.findByPan(cardOrderRequest.pan).toDto()
    }

    @ExceptionHandler(CardNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleCardNotFound() = ErrorResponse("Card not found")

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUnknownError(e: Exception) = ErrorResponse("Unexpected error: $e")

    private fun CardLookup.toDto(): CardOrderDto = CardOrderDto(pan, expirationDate)
}


class CardOrderRequest(val pan: String)
class CardOrderDto(val pan: String, expirationLocalDate: LocalDate) {
    val expirationDate: String
    val lookupTime: LocalTime = LocalTime.now()

    init {
        expirationDate = expirationLocalDate.format(DateTimeFormatter.ofPattern("MMYY"))
    }
}

class ErrorResponse(val message: String)