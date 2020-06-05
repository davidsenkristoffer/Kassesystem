package com.pos.kasse.controllers

import com.pos.kasse.config.Runner
import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Salg
import com.pos.kasse.services.KvitteringService
import com.pos.kasse.services.SalgService
import com.pos.kasse.services.VareService
import com.pos.kasse.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*
import java.time.LocalDateTime

class PaymentController : Controller() {

    private val startup: Runner by di()
    private val vareService: VareService by di()
    private val kvitteringService: KvitteringService by di()
    private val salgService: SalgService by di()
    private val vm: SubtotalAppend by inject()
    private val logger = Logger()

    fun finishSale(subtotal: Int, kvittering: Kvittering, salg: Salg): Boolean {
        var saleFinished = false
        runAsync {
            val payment = pay(subtotal)

            if (payment) {
                kvittering.datoOgTid = LocalDateTime.now()
                kvittering.sum = subtotal
                kvittering.betalingskode = "GODKJENT"

                salg.betalt = true
                salg.timestamp = kvittering.datoOgTid

                databaseCommitAsync(kvittering, salg)
                saleFinished = true
            }
            else {
                logger.alertOnMain("Betaling feilet!")
                saleFinished = false
            }
        }
        return saleFinished
    }

    //TODO: Denne koden trenger testing

    private fun databaseCommitAsync(kvittering: Kvittering, salg: Salg) =
            CoroutineScope(context = Dispatchers.Default).launch {
        val kvitteringsid = commitKvitteringToDB(kvittering)
        salg.kvitteringsid = kvitteringsid
        val salgsid = commitSalgToDB(salg)
        logger.printConsole("Salg med id $salgsid er lagt til i databasen")
    }
    private suspend fun commitKvitteringToDB(kvittering: Kvittering) = withContext(Dispatchers.Default) {
        val newKvittering: Kvittering = kvitteringService.leggTilKvittering(kvittering)
        logger.printConsole("Kvittering med id ${newKvittering.kvitteringsid} er lagt til i databasen.")
        return@withContext newKvittering.kvitteringsid
    }
    private suspend fun commitSalgToDB(salg: Salg) = withContext(Dispatchers.Default) {
        val newSalg = salgService.leggTilSalg(salg)
        return@withContext newSalg.salgsid
    }

    private fun pay(subtotal: Int): Boolean {
        var successfulPayment = false
        //TODO: Simulere betaling
        runAsync {
            timeline {
                logger.printConsole("Betal kr. $subtotal")
                keyframe(5.seconds) {
                    cycleCount = 1
                }
                successfulPayment = true
            }
        }
        return successfulPayment
    }


}