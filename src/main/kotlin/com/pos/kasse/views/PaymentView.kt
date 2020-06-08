package com.pos.kasse.views

import com.pos.kasse.controllers.PaymentController
import com.pos.kasse.controllers.PaymentController.PaymentStatus
import com.pos.kasse.controllers.SubtotalStatus
import javafx.scene.paint.Color
import tornadofx.*

class PaymentView : View() {

    private val paymentController: PaymentController by inject()
    private val pmstatus: PaymentStatus by inject()
    private val vmsub: SubtotalStatus by inject()

    override val root = borderpane {
        center {
            label(pmstatus.statusmessage)
        }
    }

    /*
    Kalles når PaymentView kobles til scenen.
    View byttes tilbake til MainWindow når funksjonen er ferdig.
     */
    override fun onDock() {
        runAsync {
            paymentController.pay(vmsub.lastNumber.get())
        }.setOnSucceeded { replaceWith(MainWindow::class, ViewTransition.FadeThrough(1.seconds, Color.TRANSPARENT)) }
    }

    /*
    Kalles når PaymentView kobles fra scenen.
    Persister kvittering og salg i databasen.
    Ferdigstiller kvitterings-objektet slik at det kan skrives ut (på skjerm)
     */
    override fun onUndock() {
        paymentController.databaseCommitAsync(PaymentController.finishedKvittering, PaymentController.finishedSale)
        paymentController.postPay(PaymentController.finishedKvittering)
        pmstatus.statusmessage.set("")
    }

}