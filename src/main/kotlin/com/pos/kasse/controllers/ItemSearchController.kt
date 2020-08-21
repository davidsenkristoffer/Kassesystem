package com.pos.kasse.controllers

import com.pos.kasse.config.Runner
import com.pos.kasse.entities.Vare
import tornadofx.*

class ItemSearchController : Controller() {

    private val runner: Runner by di()
    var itemList: List<Vare>

    init {
        itemList = runner.vareliste.asObservable()
    }

    fun searchAsync(get: String?): List<Vare> {
        if (get!!.isNotBlank()) {
           return itemList.filter { vare -> vare.navn.contains(get) }
        }
        return itemList
    }

}