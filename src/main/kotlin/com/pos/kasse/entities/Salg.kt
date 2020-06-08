package com.pos.kasse.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "salg", schema = "varer")
data class Salg(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "salgsid")
        val salgsid: Int? = null,
        @Column(name = "kvitteringsid")
        var kvitteringsid: Int? = null,
        @Column(name = "timestamp")
        var timestamp: LocalDateTime? = null,
        @Column(name = "betalt")
        var betalt: Boolean = false,
        @Column(name = "parkert")
        var parkert: Boolean = false,
        @Column(name = "mellomkunde")
        var mellomkunde: Boolean = false
) : Serializable {
    constructor(salg: Salg) : this()
}