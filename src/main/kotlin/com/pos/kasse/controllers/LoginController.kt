package com.pos.kasse.controllers

import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.utils.Logger
import tornadofx.*

class LoginController : Controller() {

    private val logger = Logger()
    private val loginService: LoginService by di()

    fun performLogin(bruker: Bruker): Int {
        val loginCode: Int
        val loginMessage: String

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

}