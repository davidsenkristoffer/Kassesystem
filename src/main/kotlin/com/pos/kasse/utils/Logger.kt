package com.pos.kasse.utils

import com.pos.kasse.views.MainWindow
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import tornadofx.*
import java.time.LocalDateTime

class Logger {

    fun printConsole(message: String) {
        println(message)
    }

    //Alert-klasse
    fun alertOnLogin(message: String) {
        val logger = Logger()
        val buttonarray: Array<ButtonType> = arrayOf(
                ButtonType.CLOSE
        )
        alert(
                Alert.AlertType.ERROR,
                header = message,
                buttons = *buttonarray,
                title = "Login",
                actionFn = {
                    buttonType ->
                    if (buttonType.buttonData == ButtonBar.ButtonData.CANCEL_CLOSE) {
                        logger.printConsole("Alert closed successfully at: ${LocalDateTime.now()}")
                    }
                }
        )
    }

}