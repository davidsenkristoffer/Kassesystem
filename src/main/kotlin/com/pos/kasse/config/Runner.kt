package com.pos.kasse.config

import com.pos.kasse.entities.Bruker
import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.KvitteringService
import com.pos.kasse.services.LoginService
import com.pos.kasse.services.VareService
import com.pos.kasse.utils.Logger
import kotlinx.coroutines.*
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalTime
import kotlin.coroutines.coroutineContext

@Component
class Runner(private val vareService: VareService, private val loginService: LoginService,
                private val kvitteringService: KvitteringService)
    : CommandLineRunner {

    /*
    Holder data om alle varer i databasen som en lokal referanse.
    Skal sjekkes for oppdateringer jevnlig.
     */
    lateinit var vareliste: List<Vare>

    /*
    Holder referanse om kvitteringer som er lagt til i databasen (siste 7 dager?)
     */
    private lateinit var byteliste: MutableList<Kvittering>

    /*
    Oppdatert kvitteringsliste med Vare-objekter basert på 'byteliste'.
     */
    lateinit var kvitteringsliste: MutableList<Kvittering>

    /*
    Logger-objekt.
     */
    private lateinit var logger: Logger

    /*
    run() utføres ved oppstart av programmet.
     */
    override fun run(vararg args: String?) {
        logger = Logger()
        kvitteringsliste = mutableListOf()

        val nyeV = listOf(
                Vare(7020655841165, "Lettkokte Havregryn Urkraft", 25,
                        "Norgesmøllene AS", sortimentskode = "A1", kategori = "Tørr"),
                Vare(7032069723586, "Økologisk Olivenolje", 85, "Kolonihagen",
                        sortimentskode = "A2", kategori = "Tørr"),
                Vare(5021991941757, "Kamillete Snore & Peace", 40,
                        "Clipper Teas AS", sortimentskode = "A2", kategori = "Tørr"),
                Vare(7032069730249, "Pizzamel Tipo 00", 35, "Kolonihagen",
                        sortimentskode = "A2", kategori = "Tørr"),
                Vare(7038080080882, "Mors Pizzagjær", 20, "Idun Industri AS",
                        sortimentskode = "A1", kategori = "Tørr")
        )
        /*
        Uncomment to add items to the database...
         */
        //leggTilVarer(nyeV)

        vareliste = vareService.hentAlleVarer()
        logger.printConsole("Totalt ${vareliste.size} antall varer...")

        byteliste = kvitteringService.hentAlleKvitteringer() as MutableList<Kvittering>
        logger.printConsole("Totalt ${byteliste.size} antall kvitteringer...")

        convertListOfKvitteringer(byteliste)
    }

    fun leggTilVarer(nyeVarer: List<Vare>) {
        vareService.leggTilAlleVarer(nyeVarer.toMutableList())
    }

    fun leggTilBruker(bruker: Bruker) {
        loginService.lagNyBruker(bruker)
    }

    suspend fun addKvitteringToList(kvittering: Kvittering) {
        if (kvitteringsliste.isEmpty()) {
            kvitteringsliste.add(kvitteringsliste.size, kvittering)
        }
        kvitteringsliste.add(kvitteringsliste.lastIndex, kvittering)
    }


    /*
    Prosess som konverterer byteliste av alle varer i et Kvitterings-objekt til Vare-objekter.
    Argumentet for å gjøre det på denne måten er at det kun skjer ved oppstart av programmet.
    Ulempen er at jobben må ferdigstilles før første bruker logges på
        TODO: Mulig at den må blokkere frem til den er ferdig?
     */
    fun convertListOfKvitteringer(mutablelist: MutableList<Kvittering>) =
            CoroutineScope(Dispatchers.Default).launch {
                if (mutablelist.isEmpty()) { coroutineContext.cancel(CancellationException("List is empty")) }
                withContext(Dispatchers.Default) {
                    mutablelist.forEach { kvittering ->
                        convertKvittering(kvittering)
                                .also { kvitteringsliste.add(it) }
                    }
                }
            }.invokeOnCompletion {
                with(logger) {
                    printConsole("Kvitteringsjobb ferdig.")
                }
            }

    /*
    Privat funksjon som kalles i convertListOfKvitteringer(...).
    Konverterer et Kvitterings-objekt.
     */
    private suspend fun convertKvittering(kvittering: Kvittering): Kvittering {
        kvittering.vareListe = mutableListOf()
        kvittering.byteliste?.forEach {
            ean -> vareliste.find { vare -> vare.ean == ean }
                .also { kvittering.vareListe!!.add(it!!) }
        }
        return kvittering
    }
}