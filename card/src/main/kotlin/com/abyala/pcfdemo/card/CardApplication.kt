package com.abyala.pcfdemo.card

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class CardApplication

fun main(args: Array<String>) {
    runApplication<CardApplication>(*args)
}