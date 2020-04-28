package com.pos.kasse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KasseApplication

fun main(args: Array<String>) {
    runApplication<KasseApplication>(*args)
}
