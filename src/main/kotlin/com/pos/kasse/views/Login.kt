package com.pos.kasse.views

import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.*

class Login : View() {

    private val loginService: LoginService by di()
    private val bruker = Bruker()
    private val brukernavnProp = SimpleStringProperty()
    private val passordProp = SimpleStringProperty()
    private var errorProp = SimpleIntegerProperty()
    private var loginCode by errorProp

    override val root = borderpane {

        top {
            label("Davidsens Matkolonial") {
                addClass(Navbar.wrapper)
            }
        }
        center {
            form {
                addClass(LoginStyle.form)
                fieldset("Login") {
                    label { //TODO: Event
                        if (loginCode == 0) {
                            hide()
                        }
                    }
                    field("Brukernavn") {
                        textfield().bind(brukernavnProp)
                    }
                    field("Passord") {
                        passwordfield().bind(passordProp)
                    }
                    button("GO...") {
                        setOnAction {
                            bruker.brukernavn = brukernavnProp.get()
                            bruker.passord = passordProp.get()
                            try {
                                loginCode = performLogin(bruker)
                            } catch (e: NoSuchElementException) {
                                e.message
                            }
                            if (loginCode == 0) {
                                this@Login.replaceWith(MainWindow::class,
                                        transition = ViewTransition.FadeThrough(1.seconds ,Color.TRANSPARENT))
                            }
                        }
                        //TODO: Midlertidig løsning på null-props.
                        disableProperty().bind(brukernavnProp.isNull.or(passordProp.isNull))
                    }
                }

            }
        }
        bottom {
            label("Tester bunntekst") {
                addClass(Footer.wrapper)
            }
        }
    }
    private fun performLogin(bruker: Bruker): Int {
        val logger = Logger()
        val loginMessage: String

        if (loginService.kontrollerLogin(bruker)) {
            loginCode = 0
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} logges inn..."
        } else {
            loginCode = 1
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} er ikke logget inn!"
        }
        logger.printConsole(loginMessage)
        return loginCode
    }
    //TODO: Overkjøre hovedvindu?
    init {
        with (root) {
            prefWidth = 400.0
            prefHeight = 200.0
            title = "Login"
        }
    }


}
