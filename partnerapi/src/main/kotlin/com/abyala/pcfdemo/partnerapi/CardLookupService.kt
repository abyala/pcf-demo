package com.abyala.pcfdemo.partnerapi

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*

@Service
class CardLookupService(private val restTemplate: RestTemplate) {
    fun findByPan(pan: String): CardLookup = try {
        restTemplate.exchange(CARD_LOOKUP_URL, HttpMethod.GET, HttpEntity<String>(headers()), CardLookup::class.java, mapOf("pan" to pan))
                .body ?: throw CardLookupException("Blank response from the server? I don't think so!")
    } catch (e: HttpClientErrorException.NotFound) {
        throw CardNotFoundException()
    } catch (e: Exception) {
        throw CardLookupException("Unable to look up card", e)
    }

    private fun headers() = HttpHeaders().apply {
        set("Authorization", "Basic ${"demo:pcf".base64Encode()}")
    }

    private fun String.base64Encode(): String =
            String(Base64.getEncoder().encode(toByteArray(Charsets.US_ASCII)))

    companion object {
        private const val CARD_LOOKUP_URL = "http://card-service/cards/{pan}"
    }
}

class CardLookup(val pan: String, val expirationDate: LocalDate)

class CardNotFoundException : Exception()
class CardLookupException(message: String, cause: Throwable? = null) : Exception(message, cause)