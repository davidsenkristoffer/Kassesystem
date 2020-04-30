package com.pos.kasse.styles

import javafx.scene.paint.Color
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass

class Navbar : Stylesheet() {

    companion object {
        val navbar by cssclass()
        private val topcolor = Color.CORNFLOWERBLUE
        private val bottomcolor = c("#FFFAF0")
    }

    init {
        navbar {
            borderColor += box(topcolor, bottomcolor)
        }
    }

}