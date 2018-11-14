package com.abyala.pcfdemo.partnerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableDiscoveryClient
class PartnerApiApplication {
    @Bean
    @LoadBalanced
    fun restTemplate(builder: RestTemplateBuilder) =
            builder.basicAuthentication("demo", "password").build()
}

fun main(args: Array<String>) {
    runApplication<PartnerApiApplication>(*args)
}