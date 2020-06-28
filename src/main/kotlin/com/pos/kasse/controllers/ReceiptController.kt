package com.pos.kasse.controllers

import com.pos.kasse.config.Runner
import com.pos.kasse.entities.Kvittering
import javafx.beans.property.SimpleObjectProperty
import tornadofx.Controller
import tornadofx.asObservable

class ReceiptController: Controller() {

    private val runner: Runner by di()

    //Gir tilgang til liste med kvitteringer
    val receiptList = runner.kvitteringsliste.asObservable()

    val receiptProp = SimpleObjectProperty<Kvittering>()

    fun display(kvittering: Kvittering) {
        receiptProp.set(kvittering)
    }

}