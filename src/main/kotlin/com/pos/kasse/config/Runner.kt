package com.pos.kasse.config

import com.pos.kasse.entities.Bruker
import com.pos.kasse.entities.Kvittering
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.KvitteringService
import com.pos.kasse.services.LoginService
import com.pos.kasse.services.VareService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

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
    Holder referanse om kvitteringer som er lagt til i databasen
     */
    lateinit var kvitteringsliste: MutableList<Kvittering>

    private val bcryptPassword: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun run(vararg args: String?) {
        vareliste = vareService.hentAlleVarer()
        println("Totalt ${vareliste.size} antall varer...")

        kvitteringsliste = kvitteringService.hentAlleKvitteringer() as MutableList<Kvittering>
        println("Totalt ${kvitteringsliste.size} antall kvitteringer...")

    }

    fun leggTilVarer(nyeVarer: List<Vare>) {
        vareService.leggTilAlleVarer(nyeVarer.toMutableList())
    }

    fun leggTilBruker(bruker: Bruker) {
        loginService.lagNyBruker(bruker)
    }

    suspend fun addKvitteringToList(kvittering: Kvittering) {
        kvitteringsliste.add(kvitteringsliste.lastIndex, kvittering)
    }
}