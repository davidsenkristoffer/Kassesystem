package com.pos.kasse.controllers

import com.pos.kasse.services.VareService
import spark.kotlin.Http
import spark.kotlin.ignite


//TODO: Implementere resten...

class RestController(private val vareService: VareService) {

    private val vareliste = vareService.hentAlleVarer()

    fun main(args: Array<String>) {
        val http: Http = ignite()

        http.after {
            response.type("application/json")
        }
    }



}