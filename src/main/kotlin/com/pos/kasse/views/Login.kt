package com.pos.kasse.views

import com.pos.kasse.controllers.LogginnController
import com.pos.kasse.entities.Bruker
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import kotlin.properties.Delegates

class Login : View() {

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

        private val bruker = Bruker()
        private val logginnController: LogginnController by inject()
        private var loginCode by Delegates.notNull<Int>()

        override val root = form {
            addClass(LoginStyle.form)
            fieldset("Login") {
                field("Brukernavn") {
                    textfield().bind(observable(bruker, Bruker::brukernavn))
                }
                field("Passord") {
                    textfield().bind(observable(bruker, Bruker::passord))
                }
                button("GO...") {
                    setOnAction {
                        loginCode = logginnController.performLogin(bruker)
                        if (loginCode == 0) {
                            replaceWith<MainWindow>(transition = fade()) //Noe i denne duren, kompilerer ikke...
                        }
                    }
                    disableProperty().bind(observable(bruker, Bruker::brukernavn).isNull
                            .or(observable(bruker, Bruker::passord).isNull))
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
