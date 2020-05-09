package com.pos.kasse.styles

import tornadofx.*

class Navbar : Stylesheet() {

    companion object {
        val navbar by cssclass()
        val wrapper by cssclass()
        val navfont = loadFont("/Inconsolata-Light.ttf", 24.0)!!
    }

    init {
        wrapper {
            padding = box(10.px)
            spacing = 10.px
            font = navfont
            label {
                fontSize = 32.px
                padding = box(5.px, 10.px)
            }
        }
        navbar {
            //Farge og stil etc
        }
    }

}