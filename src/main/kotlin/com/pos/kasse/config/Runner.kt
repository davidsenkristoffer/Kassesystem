package com.pos.kasse.config

import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner(private val vareService: VareService) : CommandLineRunner {

    lateinit var vareliste: List<Vare>

    /*
    val nyevarer = mutableListOf<Vare>(
            Vare(1234567890123, "drue", 10, "blå drue",
                    null, "F9", "Frukt"),
            Vare(1234567890124, "appelsin", 10, "oransje appelsin",
                    700, "F9", "Frukt"),
            Vare(1234567890125, "eple", 10, "gul eple",
                    722, "F9", "Frukt"),
            Vare(1234567890126, "poteter", 22, "brune poteter",
                    743, "F9", "Frukt"),
            Vare(1234567890128, "banan hvit", 35, "gul banan",
                    711, "F9", "Frukt"),
            Vare(1234567890129, "gulrøtter", 21, "oransje gulrøtter",
                    4444, "F9", "Frukt"),
            Vare(1234567890130, "ruccula", 19, "grønn ruccula",
                    1122, "F9", "Frukt"),
            Vare(1234567890147, "smoothie", 10, "blå smoothie",
                    null, "F9", "Frukt")
    )

     */

    override fun run(vararg args: String?) {

        vareliste = vareService.hentAlleVarer()
        println("Totalt ${vareliste.size} antall varer...")
    }

    fun leggTilVarer(nyeVarer: List<Vare>) {
        vareService.leggTilAlleVarer(nyeVarer.toMutableList())
    }
}