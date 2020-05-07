package com.pos.kasse

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Tests {

    val mapper: ObjectMapper = jacksonObjectMapper().registerModule(KotlinModule())

    @Test
    fun contextLoads() {
    }

    @Test
    fun serialize() {
        val vare = Vare(1234567890123, "banan", 10, "gul banan",
                710, "F9", Kategori.FRUKT)
        val serialized = mapper.writeValueAsString(vare)

        val json = """
      {
        "ean": "1234567890123",
	    "navn": "banan",
	    "pris": "10",
        "beskrivelse": "gul banan",
        "plu": "710",
        "sortimentskode": "F9",
        "kategori": "FRUKT"
    }"""
        assertEquals(serialized, json)
    }

}
