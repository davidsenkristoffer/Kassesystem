package com.pos.kasse.controllers

import com.pos.kasse.config.Runner
import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Salg
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.SalgService
import com.pos.kasse.services.VareService
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.Controller
import tornadofx.FXEvent
import tornadofx.ViewModel
import tornadofx.asObservable

class SalesController : Controller() {

    private val startup: Runner by di()
    private val vareService: VareService by di()
    private val salgService: SalgService by di()
    private val vm: SubtotalAppend by inject()
    private val logger = Logger()

    //Liste av varer for det aktuelle salget.
    var observablelist = mutableListOf<Vare>().asObservable()

    //Referanse for liste av varer i databasen.
    private val varelisten = startup.vareliste.asObservable()

    //Holder siste kvittering
    private lateinit var lastReceipt: Kvittering

    fun initNewSale(): Salg {
        return Salg()
    }

    fun checkEanInputLength(input: Int): Boolean {
        return input > 15
    }

    fun createNewKvittering(): Kvittering {
        return Kvittering()
    }

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

    fun checkItems(): Boolean {
        return observablelist.any()
    }

    fun cleanObservablelist() {
        observablelist.clear()
    }

    fun writeLastReceipt() {
        logger.printReceipt(lastReceipt)
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