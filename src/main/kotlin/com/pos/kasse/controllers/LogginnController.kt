package com.pos.kasse.controllers

import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.utils.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import tornadofx.Controller
import kotlin.properties.Delegates

@Component
class LogginnController: Controller() {

    @Autowired
    private lateinit var loginService: LoginService

    val logger = Logger()
    var loginMessage: String = ""
    var loginCode by Delegates.notNull<Int>()

    fun performLogin(bruker: Bruker): Int {
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

}