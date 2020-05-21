package com.pos.kasse.entities
import kotlinx.serialization.*
import java.io.Serializable
import javax.persistence.*

/**
 * Endre pris til double
 * Legge til flere beskrivelser, f.eks. opprinnelsesland
 * Generere tilfeldig EAN? Lage en utils-metode
 */

@Entity
@javax.persistence.Table(name = "vare", schema = "varer")
data class Vare(
        @Id
        val ean: Long = 0,
        var navn: String = "",
        var pris: Int = 0,
        var beskrivelse: String = "",
        val plu: Int? = null,
        var sortimentskode: String = "",
        var kategori: String = ""
    ) : Serializable {
    constructor(vare: Vare) : this()
}