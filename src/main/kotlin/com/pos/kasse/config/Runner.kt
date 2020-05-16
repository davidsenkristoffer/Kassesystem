package com.pos.kasse.config

import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import org.springframework.boot.CommandLineRunner

class Runner(private val vareService: VareService) : CommandLineRunner {

    override fun run(vararg args: String?) {

        //var vareliste = vareService.hentAlleVarer()

    }
}