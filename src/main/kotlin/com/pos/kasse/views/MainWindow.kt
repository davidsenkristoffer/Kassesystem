package com.pos.kasse.views

import tornadofx.Stylesheet.Companion.label
import tornadofx.View
import tornadofx.*

class MainWindow : View() {


    override val root = borderpane {
        top {
            label("test")
        }
    }
}