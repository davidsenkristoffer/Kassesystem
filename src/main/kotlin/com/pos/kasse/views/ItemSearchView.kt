package com.pos.kasse.views

import com.pos.kasse.styles.Navbar
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import tornadofx.*

class ItemSearchView : View() {

    override val root = borderpane {

        top = label("Davidsens Matkolonial") {
            addClass(Navbar.wrapper) //adding stylesheet to borderpane
        }

        left = vbox {
            button("Test1") {
                useMaxSize = true
            }
            button("test2") {
                useMaxSize = true
            }
        }

    }
    /**
     * TableView
     */

}