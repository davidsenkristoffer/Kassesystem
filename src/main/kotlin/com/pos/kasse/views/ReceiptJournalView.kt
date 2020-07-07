package com.pos.kasse.views

import com.pos.kasse.controllers.ReceiptController
import com.pos.kasse.entities.Kvittering
import javafx.scene.Parent
import javafx.scene.control.SelectionMode
import javafx.scene.paint.Color
import tornadofx.*

class ReceiptJournalView : View() {

    private val receiptController: ReceiptController by inject()

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
                readonlyColumn("ID", Kvittering::kvitteringsid)
                readonlyColumn("Tidspunkt", Kvittering::datoOgTid)
                selectionModel.selectionMode = SelectionMode.SINGLE
                maxWidth = 400.0
                onUserSelect(clickCount = 1) {
                    //TODO: NoClassDefFoundError
                    kvittering -> receiptController.display(kvittering)
                }
            }
        }
        //Forhåndsvisning av kvitteringen
        right {
            //TODO: Forenkle
            if (receiptController.receiptProp.get() != null) {
                text(receiptController.receiptProp.get().toString())
            }
        }
        //Ulike funksjoner
        bottom {

        }
    }

}