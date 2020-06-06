package com.pos.kasse.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kvittering", schema = "varer")
data class Kvittering(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val kvitteringsid: Int = 0,
        var datoOgTid: LocalDateTime? = null,
        @OneToMany(mappedBy = "ean")
        var vareListe: List<Vare>? = null,
        var sum: Int? = null,
        var betalingskode: String? = null
) : Serializable {
    constructor(kvittering: Kvittering) : this()

    override fun toString(): String {
        TODO("Implement visual representation of Kvittering.")
    }

}