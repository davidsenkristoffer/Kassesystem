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

        //27.okt
        val nyeV2 = listOf(
                Vare(7070866027872, "Möllers Dobbel", 89, "Orkla Health AS",null, "A1","Kategori"),
                Vare(7026450051051, "Honning Akasie", 55, "Honningcentralen SA", null, "A1", "Tørr"),
                Vare(7026450030124, "Sommerhonning", 69, "Honningcentralen SA", null, "A2", "Tørr"),
                Vare(8003496060024, "Finhakkede Tomater", 13, "Gruppo Petti S.p.A.", null, "A1", "Tørr"),
                Vare(70111019, "Tomatpuré", 5, "Stavland AS", null, "A1", "Tørr"),
                Vare(7622210115782, "Vaniljesukker", 20, "Mondelez Norge", null, "A1", "Tørr"),
                Vare(7032069724538, "Sesamfrø Økologisk", 29, "Automatpack AS for Rema 1000 AS", null, "A1", "Tørr"),
                Vare(7622210115720, "Bakepulver", 20, "Mondelez Norge", null, "A1", "Tørr"),
                Vare(7032069724514, "Solsikkekjerner Økologisk", 29, "Automatpack AS for Rema 1000 AS", null, "A1", "Tørr"),
                Vare(7032069731055, "Pizzasaus Økologisk", 25, "Fattorie Umbre srl.", null, "A1", "Tørr"),
                Vare(7032069714812, "Mais 3 pk", 6, "Seneca Foods Corporation", null, "A1", "Tørr"),
                Vare(8076808201293, "Tagliatelle All' Uovo", 39, "Barilla Norge AS", null, "A1", "Tørr"),
                Vare(7038010019777, "Jarlsberg Skivet", 43, "Tine SA", null, "A1", "Kjøl"),
                Vare(7070097079022, "Parmaskinke", 39, "Taga Foods AS", null, "A2", "Kjøl")

        )
        /*
        Uncomment to add items to the database...
         */
        leggTilVarer(nyeV2)

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