package com.pos.kasse.views

import com.pos.kasse.services.VareService
import tornadofx.*

class MainWindow : View() {

    private val vareService: VareService by di()

    override val root = borderpane {
        center {
            label("varelinjer, subtotal, inputlinje")
        }
        top {
            label("Klokke, dato, brukerinfo, ???")
        }
        bottom {
            label("Alle knappene her...")
        }
        right {
            label("async forhåndsvisning av varer? plu-basert? Forhåndsvis-knapp? Bilde?")
        }
        left {
            label("vettafaen...")
        }
    }
}