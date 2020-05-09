package com.pos.kasse.styles

import javafx.scene.paint.Color
import tornadofx.*

class MainWindowStyle : Stylesheet() {

    private val oddcolor = Color.BLANCHEDALMOND
    private val evencolor = Color.WHITE

    companion object {
        val tableclass by cssclass()
        val customfont = loadFont("/Inconsolata-Light.ttf", 24.0)!!
    }
    init {
        tableclass {

            tableView {
                tableCell {
                    backgroundColor += Color.TRANSPARENT
                }
                tableRowCell {
                    and (odd) {
                        backgroundColor += oddcolor
                    }
                    and (even) {
                        backgroundColor += evencolor
                    }
                }
                font = customfont
                padding = box(5.px)
                spacing = 5.px
            }
        }
    }
}