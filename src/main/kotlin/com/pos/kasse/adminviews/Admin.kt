package com.pos.kasse.adminviews

import javafx.scene.Parent
import tornadofx.*

class Admin : View() {

    override val root = borderpane {
        center {
            label("Hello")
        }
    }
}