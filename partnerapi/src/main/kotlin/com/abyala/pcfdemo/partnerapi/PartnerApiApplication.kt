package com.abyala.pcfdemo.partnerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class PartnerApiApplication {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder) =
            builder.basicAuthentication("demo", "password").build()
}

fun main(args: Array<String>) {
    runApplication<PartnerApiApplication>(*args)
}