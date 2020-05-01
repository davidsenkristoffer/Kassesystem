package com.pos.kasse.views

import com.pos.kasse.controllers.LogginnController
import com.pos.kasse.entities.Bruker
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.styles.Navbar
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Login : View() {

    private val loginController: LogginnController by inject()
    private val input = SimpleStringProperty()
    private val topView: Top by inject()
    private val bottomView: Bottom by inject()
    private val centerView: Center by inject()

    override val root = borderpane {
        top = topView.root
        center = centerView.root
        bottom = bottomView.root
    }

    class Top : View() {

        override val root = label("Davidsens Matkolonial") {
            addClass(Navbar.wrapper)
        }
    }

    class Center : View() {

        private val bruker: Bruker by inject()

        override val root = form {
            addClass(LoginStyle.form)

            fieldset("Login") {
                field("Brukernavn") {
                    textfield(observable(bruker, Bruker::brukernavn)).required()
                }
                field("Passord") {
                    textfield(observable(bruker, Bruker::passord)).required()
                }
            }

        }
    }
    
    class Bottom : View() {
        override val root = label("Tester bunntekst") {
            addClass(Footer.wrapper)
        }

    }
}