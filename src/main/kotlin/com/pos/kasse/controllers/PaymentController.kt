package com.pos.kasse.controllers

import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Salg
import com.pos.kasse.services.KvitteringService
import com.pos.kasse.services.SalgService
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleStringProperty
import kotlinx.coroutines.*
import tornadofx.Controller
import tornadofx.FXEvent
import tornadofx.ViewModel
import java.time.LocalDateTime

class PaymentController : Controller() {

    private val kvitteringService: KvitteringService by di()
    private val salgService: SalgService by di()
    private val logger = Logger()
    private val pmAppend: PaymentAppend by inject()
    private val salesController: SalesController by inject()

    /*
    Kalles når MainView skifter til PaymentView
    Simulerer en betaling, som pr nå alltid godkjennes
     */
    fun pay(subtotal: Int): Boolean {
        var successfulPayment = false
        runBlocking {
            pmAppend.save("Betal kr. $subtotal")
            logger.printConsole("Betal kr. $subtotal")
            delay(2000)
            pmAppend.save("Betaler...")
            logger.printConsole("Betaler...")
            delay(5000)
            successfulPayment = true
            pmAppend.save("Godkjent!")
            logger.printConsole("godkjent")
        }

        if (successfulPayment) {
            finishSale(subtotal, salesController.sale)
        } else {
            pmAppend.save("Ikke godkjent, prøv igjen!")
            //Delay her?
            Thread.sleep(2000)
            pay(subtotal)
        }
        return successfulPayment
    }

    /*
    Kalles når betaling er godkjent.
    Gjør rutineoppgaver på kvitterings- og salgsobjekt.
    Synkroniserer objekter med databasen.
     */
    private fun finishSale(subtotal: Int, salg: Salg) {
        val kvittering = createNewKvittering()
        runBlocking {
            kvittering.datoOgTid = LocalDateTime.now()
            kvittering.sum = subtotal
            kvittering.betalingskode = "GODKJENT"

            salg.betalt = true
            salg.timestamp = kvittering.datoOgTid
        }

        databaseCommitAsync(kvittering, salg)
        // fire(replacewindow event her)
    }

    private fun createNewKvittering(): Kvittering {
        return Kvittering(
                vareListe = salesController.observablelist,
                liste = salesController.observablelist.map { vare -> vare.ean }.toLongArray()
        )
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

//Legger til statusmelding ved bruk av FXEvent.
class PaymentAppend : ViewModel() {
    fun save(message: String) {
        fire(PaymentEvent(message))
    }
}
//Viser statusmelding i PaymentView.
class PaymentStatus : ViewModel() {
    val statusmessage = SimpleStringProperty()
    init {
        subscribe<PaymentEvent> {
            statusmessage.value = it.message
        }
    }
}
//Event som holder siste melding
class PaymentEvent(val message: String) : FXEvent()


}