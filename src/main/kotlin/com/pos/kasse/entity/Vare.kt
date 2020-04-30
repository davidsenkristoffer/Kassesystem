package com.pos.kasse.entity

import javax.persistence.*

@Entity
@Table(name = "vare", schema = "varer")
data class Vare(
        @Id
        val ean: Long = 0L,
        var navn: String = "",
        var pris: Int = 0,
        var beskrivelse: String = "",
        val plu: Int? = null,
        var sortimentskode: String = "",
        var kategori: Enum<Kategori>? = Kategori.INGEN
    ) {
    constructor(vare: Vare) : this()

}