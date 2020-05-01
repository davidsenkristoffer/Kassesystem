package com.pos.kasse.styles

import tornadofx.*

class Footer : Stylesheet() {

    companion object {
        val wrapper by cssclass()
        val bottomfont = loadFont("/Inconsolata-Light.ttf", 16.0)!!
    }
    init {
        wrapper {
            font = bottomfont
            padding = box(10.px)
            spacing = 10.px
        }
    }
}