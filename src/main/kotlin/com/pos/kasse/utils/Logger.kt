package com.pos.kasse.utils

import com.pos.kasse.views.MainWindow
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import tornadofx.*
import java.time.LocalDateTime

class Logger {

    private val buttonarray: Array<ButtonType> = arrayOf(
            ButtonType.CLOSE
    )

    fun printConsole(message: String) {
        println(message)
    }

    //Alert-klasse
    fun alertOnLogin(message: String) {
        alert(
                Alert.AlertType.ERROR,
                header = message,
                buttons = *buttonarray,
                title = "Login",
                actionFn = {
                    buttonType ->
                    if (buttonType.buttonData == ButtonBar.ButtonData.CANCEL_CLOSE) {
                        printConsole("Alert ${Alert.AlertType.ERROR} closed successfully at: ${LocalDateTime.now()}")
                    }
                }
        )
    }

    fun alertOnMain(error: String) {
        alert(
                Alert.AlertType.WARNING,
                header = error,
                buttons = *buttonarray,
                title = "Informasjon",
                actionFn = {
                    buttonType ->
                    if (buttonType.buttonData == ButtonBar.ButtonData.CANCEL_CLOSE) {
                        printConsole("Alert ${Alert.AlertType.WARNING} closed successfully at: ${LocalDateTime.now()}")
                    }
                }
        )
        }
    }

}