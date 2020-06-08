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
            label("test")
        }
    }

    //Kalles når PaymentView er iscenesatt.
    override fun onDock() {
        paymentController.pay(vmsub.lastNumber.get())
    }

    override fun onUndock() {
        paymentController.databaseCommitAsync(PaymentController.finishedKvittering, PaymentController.finishedSale)
        paymentController.postPay(PaymentController.finishedKvittering)
    }

}