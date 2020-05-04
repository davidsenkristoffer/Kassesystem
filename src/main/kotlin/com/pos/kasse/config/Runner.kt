package com.pos.kasse.config

import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import org.springframework.boot.CommandLineRunner

class Runner(private val vareService: VareService) : CommandLineRunner {

    override fun run(vararg args: String?) {

        //var vareliste = vareService.hentAlleVarer()

        var vareliste = mutableListOf(
                Vare(1234567890123, "Bendit røde druer", 25, "Opprinnelsesland: Spania", null, "F9", Kategori.FRUKT),
                Vare(2345678901234, "Laks MUH", 69, "Opprinnelsesland: Norge", null, "A1", Kategori.KJOEL)
        )
        vareService.leggTilAlleVarer(vareliste)

    }
}