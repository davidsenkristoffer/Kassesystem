package com.pos.kasse.styles

import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import tornadofx.*

class MainWindowStyle : Stylesheet() {

    private val oddcolor = Color.ALICEBLUE
    private val evencolor = Color.WHITE

    companion object {
        val tableclass by cssclass()
        val customfont = loadFont("/Inconsolata-Light.ttf", 16.0)!!
    }
    init {
        tableclass {
            tableView {
                tableCell { backgroundColor += Color.TRANSPARENT }
                tableRowCell {
                    and (odd) { backgroundColor += oddcolor }
                    and (even) { backgroundColor += evencolor }
                }
                scrollBar {
                    showTickLabels = true
                    showTickMarks = true
                    showWeekNumbers = true
                }
                font = customfont
                padding = box(5.px)
                spacing = 5.px
            }
            label {
                font = customfont
                padding = box(5.px)
                spacing = 5.px
            }
        }
    }
}