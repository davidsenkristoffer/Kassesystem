package com.pos.kasse.entity

import javax.persistence.*

@Entity
@Table(name = "vare", schema = "varer")
class Vare
    /**
     * Brukes dersom man henter all informasjon om en vare.
     * @param ean
     * @param navn
     * @param pris
     * @param beskrivelse
     * @param plu
     * @param sortimentskode
     */
    (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ean")
    private var ean: Long,
    @Column(name = "navn")
    private var navn: String?,
    @Column(name = "pris")
    private var pris: Int,
    @Column(name = "beskrivelse")
    private var beskrivelse: String?,
    @Column(name = "plu")
    private var plu: Int,
    @Column(name = "sortimentskode")
    private var sortimentskode: String?,
    @Column(name = "kategori")
    private var kategori: Enum<Kategori>?)
    {

    override fun toString(): String {
        return "Vare{" +
                "ean=" + ean +
                ", navn='" + navn + '\'' +
                ", pris=" + pris +
                ", plu=" + plu +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", sortimentskode='" + sortimentskode + '\'' +
                ", kategori=" + kategori +
                '}'
    }
}