package com.pos.kasse.messagequeue

import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Salg
import com.pos.kasse.entities.Vare
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object Salgsmelding {

    fun createMelding(salg: Salg, kvittering: Kvittering): String {
        val salgsMap: MutableMap<Vare, Double> = mutableMapOf()
        if (salg.kvitteringsid == kvittering.kvitteringsid) {
            kvittering.vareListe?.forEach {
                salgsMap.putIfAbsent(it, 1.0) ?: salgsMap[it] to salgsMap[it]?.plus(1.0)
            }
        }
        return Json.encodeToString(salgsMap)
    }

}