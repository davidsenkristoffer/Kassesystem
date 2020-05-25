package com.pos.kasse.controllers

import com.pos.kasse.config.Runner
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class MainWindowController : Controller() {

    private val startup: Runner by di()
    private val vareService: VareService by di()
    private val vm: SubtotalAppend by inject()

    var observablelist = mutableListOf<Vare>().asObservable()
    private val varelisten = startup.vareliste.asObservable()

    fun addPluToTable(input: Long) {
        runAsync {
            varelisten.forEach {vare ->
                if (vare.plu == input.toInt()) {
                    observablelist.add(vare)
                    vm.save(vare.pris)
                }
            }
        }
    }

    fun addEanToTable(ean: Long) {
        runAsync {
            varelisten.forEach {vare ->
                if (vare.ean == ean) {
                    observablelist.add(vare)
                    vm.save(vare.pris)
                }
            }
        }
    }

}


//Legger til pris ved bruk av FXEvent.
class SubtotalAppend : ViewModel() {
    fun save(pris: Int) {
        fire(DataSavedEvent(pris))
    }
}

//Viser subtotal i View.
class SubtotalStatus : ViewModel() {
    val lastNumber = SimpleIntegerProperty()
    init {
        subscribe<DataSavedEvent> {
            lastNumber.value += it.message
        }
    }
}
//Event som holder siste pris
class DataSavedEvent(val message: Int) : FXEvent()