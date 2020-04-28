package com.pos.kasse.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kvittering", schema = "varer")
data class Kvittering constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kvitteringsid")
    private val kvitteringsid: Int,

    @Column(name = "datoogtid")
    private val datoOgTid: LocalDateTime?,

    @Column(name = "vareliste")
    private val vareListe: List<Vare>?
)