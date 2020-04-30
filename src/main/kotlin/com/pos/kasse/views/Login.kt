package com.pos.kasse.views

import com.pos.kasse.controllers.LogginnController
import javafx.beans.property.SimpleStringProperty
import tornadofx.View
import tornadofx.borderpane
import tornadofx.label

class Login : View() {

    private val loginController: LogginnController by inject()
    private val input = SimpleStringProperty()
    private val topView: Top by inject()
    private val bottomView: Bottom by inject()

    override val root = borderpane {
        top = topView.root
        bottom = bottomView.root
    }

    class Top : View() {

        override val root = label("top") {

        }
    }
    
    class Bottom : View() {
        override val root = label("bottom")

    }
}