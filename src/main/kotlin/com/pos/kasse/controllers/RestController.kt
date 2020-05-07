package com.pos.kasse.controllers

import com.google.gson.Gson
import com.pos.kasse.entities.Bruker
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import org.eclipse.jetty.http.HttpHeader
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import spark.Filter
import spark.Redirect
import spark.Spark
import spark.Spark.*


//TODO: Implementere resten...
// Returnerer bare 404, noe konfigurering trengs...

class RestController(private val vareService: VareService) {

    /*
    fun main(args: Array<String>) {

        val vareliste = vareService.hentAlleVarer()
        val gson = Gson()
        val bruker = Bruker("test", "test2")
        after(
                Filter {
                    _, response -> response.type("application/json")
                }
        )
        get("/list") {
            _, _ ->
            gson.toJson(vareliste)
        }
        post("/add") {
            request, response ->
            val vare = vareService.leggTilVare(
                    gson.fromJson(request.body(), Vare::class.java)
            )
            response.body(gson.toJson(vare))
        }
    }

     */
}