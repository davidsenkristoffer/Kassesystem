package com.pos.kasse.views

import com.pos.kasse.services.VareService
import tornadofx.*

class MainWindow : View() {

    private val vareService: VareService by di()
    override val root = borderpane {
        top {
            label("test")
        }
    }
}