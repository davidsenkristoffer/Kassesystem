package com.pos.kasse.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kvittering", schema = "varer")
data class Kvittering(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "kvitteringsid")
        val kvitteringsid: Int = 0,
        @Column(name = "datoogtid")
        var datoOgTid: LocalDateTime? = null,
        //Liste på applikasjonsnivå
        @Transient
        var vareListe: MutableList<Vare>? = null,
        //Referanseliste på databasenivå
        @Column(name = "byteliste")
        var byteliste: LongArray? = null,
        @Column(name = "sum")
        var sum: Int? = null,
        @Column(name = "betalingskode")
        var betalingskode: String? = null
) : Serializable {
    constructor(kvittering: Kvittering) : this()

    override fun toString(): String {
        //TODO: Implementer resten av toString()
        var space: Int = 0
        for (item in vareListe!!) {
            if (space < item.navn.length) {
                space = item.navn.length
            }
        }
        val firstline = "ID: $kvitteringsid \t\t\t ${datoOgTid?.dayOfMonth}.${datoOgTid?.monthValue}.${datoOgTid?.year} \n"
        val line = "--------------------------------- \n"
        val secondline = "Navn \t\t\t Pris \n"
        val thirdline = "Subtotal: \t\t\t $sum \n $betalingskode"
        return firstline + secondline + line + vareListe?.joinToString(separator = "\n") {
            vare -> String.format("%s %${calcSpaces(vare)}s %s", vare.navn, " ", vare.pris)
        } + "\n" + line + thirdline
    }

    fun calcSpaces(item: Vare): Int {
        val max = 60
        return max - item.navn.length
    }

}