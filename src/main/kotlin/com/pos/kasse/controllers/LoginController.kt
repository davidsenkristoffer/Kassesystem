package com.pos.kasse.controllers

import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class LoginController : Controller() {

    companion object MainWindow {
        val bruker = Bruker()
    }

    private val logger = Logger()
    private val loginService: LoginService by di()
    val brukernavnProp = SimpleStringProperty()
    val passordProp = SimpleStringProperty()
    private var loginCode = 0
    private var loginMessage = ""

    fun checkEmptyProps() : Boolean {
        return (passordProp.value.isNullOrBlank() ||
                brukernavnProp.value.isNullOrBlank())
    }

    fun setProps() {
        bruker.brukernavn = brukernavnProp.value
        bruker.passord = passordProp.value
    }

    fun performLogin() : Int {

        if (loginService.kontrollerLogin(bruker)) {
            loginCode = 2
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} logges inn..."
        } else {
            loginCode = 1
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} er ikke logget inn!"
        }
        logger.printConsole(loginMessage)
        return loginCode
    }

    fun performAdminLogin() : Int {
        if (loginService.kontrollerAdminLogin(bruker)) {
            loginCode = 2
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} logges inn..."
        } else {
            loginCode = 1
            loginMessage = "Kode: ${loginCode}, ${bruker.brukernavn} er ikke logget inn!"
        }
        return loginCode
    }

    fun cleanBrukerProps() {
        bruker.brukernavn = ""
        bruker.passord = ""
        bruker.privilege = false
    }

    fun cleanFields() {
        brukernavnProp.set("")
        passordProp.set("")
    }

}