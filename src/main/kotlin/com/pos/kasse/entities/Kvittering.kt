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
        var vareListe: List<Vare>? = null,
        //Referanseliste på databasenivå
        @Column(name = "liste")
        var liste: LongArray? = null,
        @Column(name = "sum")
        var sum: Int? = null,
        @Column(name = "betalingskode")
        var betalingskode: String? = null
) : Serializable {
    constructor(kvittering: Kvittering) : this()

    override fun toString(): String {
        TODO("Implement visual representation of Kvittering.")
    }

}