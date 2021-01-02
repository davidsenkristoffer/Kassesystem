package com.pos.kasse.messagequeue

import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Salg
import com.pos.kasse.entities.Vare
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Salgsmelding {

    fun createMelding(salg: Salg, kvittering: Kvittering): String {
        val salgsMap: MutableMap<Long, Double> = mutableMapOf()
        kvittering.vareListe?.forEach {
            salgsMap.putIfAbsent(it.ean, 1.0)
                ?: salgsMap[it.ean] to salgsMap[it.ean]?.plus(1.0)
            //TODO: Fikse denne if-else løkken. Nåværende metode er litt fyfy.
            /*
        if (salg.kvitteringsid == kvittering.kvitteringsid) {

            }
        } else {
            error("Id for Salg og kvittering er ulik...")
        }
         */
        }
        return Json.encodeToString(salgsMap)
    }
}