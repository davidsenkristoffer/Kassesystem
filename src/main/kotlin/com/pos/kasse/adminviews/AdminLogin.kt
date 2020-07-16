package com.pos.kasse.adminviews

import com.pos.kasse.controllers.LoginController
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.utils.Logger
import com.pos.kasse.views.MainWindow
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.paint.Color
import tornadofx.*

class AdminLogin : View() {

    private val loginController: LoginController by inject()
    private val logger = Logger()
    private val errorProp = SimpleIntegerProperty()
    var loginCode by errorProp

    override val root = borderpane {
        center {
            form {
                addClass(LoginStyle.form)
                fieldset("Login") {
                    field("Brukernavn") {
                        textfield().bind(loginController.brukernavnProp)
                    }
                    field("Passord") {
                        passwordfield().bind(loginController.passordProp)
                    }
                    button("Logg inn") {
                        setOnAction {
                            if (loginController.checkEmptyProps()) {
                                logger.alertOnLogin("Passord eller brukernavn kan ikke være tom!")
                                return@setOnAction
                            } else {
                                loginController.setProps()
                            }
                            try {
                                loginCode = loginController.performAdminLogin()
                            } catch (e: NoSuchElementException) {
                                e.message
                            }
                            if (loginCode == 2) {
                                this@AdminLogin.replaceWith(Admin::class,
                                        transition = ViewTransition.FadeThrough(1.seconds, Color.TRANSPARENT))
                            } else {
                                logger.alertOnLogin("Feil brukernavn eller passord, eller du har ikke privilegier.")
                            }
                        }
                    }.isDefaultButton = true
                }
            }.requestFocus()
        }

    }

}