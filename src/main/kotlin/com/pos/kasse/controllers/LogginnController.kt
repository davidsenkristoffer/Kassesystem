package com.pos.kasse.controllers

import com.pos.kasse.services.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import tornadofx.Controller

@Component
class LogginnController: Controller() {

    @Autowired
    private lateinit var loginService: LoginService

    // test...
    fun print(input: String) {
        println("Hello, $input!")
    }

}