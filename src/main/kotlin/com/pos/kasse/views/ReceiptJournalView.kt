package com.pos.kasse.views

import com.pos.kasse.config.Runner
import com.pos.kasse.controllers.ReceiptController
import com.pos.kasse.entities.Kvittering
import com.pos.kasse.utils.Logger
import javafx.scene.Parent
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import tornadofx.*
import java.time.LocalDateTime

class ReceiptJournalView : View() {

    private val receiptController: ReceiptController by inject()
    private val logger = Logger()
    private var tempKvi = Kvittering()

    override val root = borderpane {
        //Knapp som tar bruker tilbake til hovedvindu
        top {
            button("<--") {
                setOnAction {
                    this@ReceiptJournalView.replaceWith(MainWindow::class,
                            ViewTransition.FadeThrough(1.seconds, Color.TRANSPARENT))
                }
            }
        }
        //Tabell med kvitteringer
        center {
            tableview(receiptController.receiptList) {
                readonlyColumn("ID", Kvittering::kvitteringsid) { sortOrder.add(this) }
                readonlyColumn("Tidspunkt", Kvittering::datoOgTid) {
                    //TODO: Formater kolonnen
                }
                selectionModel.selectionMode = SelectionMode.SINGLE
                maxWidth = 400.0
                //TODO: Fungerer ikke pga feil versjon av Java og JavaFX...
                onUserSelect { kvittering -> receiptController.display(kvittering) }
            }
        }
        right {
            if (receiptController.receiptProp.get() != null) {
                text(receiptController.receiptProp.get().toString())
            }
        }
        //Ulike funksjoner
        bottom {
        }
    }

}