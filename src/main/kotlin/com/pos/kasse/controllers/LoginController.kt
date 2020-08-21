package com.pos.kasse.controllers

import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class LoginController : Controller() {

    private val logger = Logger()
    private val loginService: LoginService by di()
    val brukernavnProp = SimpleStringProperty()
    val passordProp = SimpleStringProperty()
    private var loginCode = 0
    private var loginMessage = ""

    companion object MainWindow {
        lateinit var bruker: Bruker
    }

    init {
        bruker = initNewBruker()
    }

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

    /*
    TODO: Fikse loggut -> logginn sekvens.
    onDock()
     */
    fun cleanBrukerProps() {
        bruker = initNewBruker()
        bruker.privilege = false
    }

    /*
    onUndock()
     */
    fun cleanFields() {
        brukernavnProp.value = ""
        passordProp.value = ""
        loginCode = 0
    }

    private fun initNewBruker() : Bruker {
        return Bruker()
    }

}