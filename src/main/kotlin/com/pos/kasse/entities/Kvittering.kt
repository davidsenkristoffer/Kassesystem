package com.pos.kasse.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kvittering", schema = "varer")
data class Kvittering(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val kvitteringsid: Int = 0,
        val datoOgTid: LocalDateTime = LocalDateTime.now(),
        @OneToMany(mappedBy = "ean")
        val vareListe: List<Vare> = mutableListOf(),
        val sum: Int = 0
) : Serializable {
    constructor(kvittering: Kvittering) : this()
}