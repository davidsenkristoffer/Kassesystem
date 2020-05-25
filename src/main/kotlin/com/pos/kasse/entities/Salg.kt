package com.pos.kasse.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "salg", schema = "vare")
data class Salg(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val salgsid: Int = 0,
        val kvitteringsid: Int = 0,
        val timestamp: LocalDateTime = LocalDateTime.now(),
        var betalt: Boolean = false,
        var parkert: Boolean = false,
        var mellomkunde: Boolean = false
) : Serializable {
    constructor(salg: Salg) : this()
}