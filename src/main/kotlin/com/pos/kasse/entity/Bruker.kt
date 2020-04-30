package com.pos.kasse.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bruker", schema = "varer")
data class Bruker(
        @Id
        val brukernavn: String = "",
        var passord: String = ""
) {
    init {
        if (brukernavn.isBlank() || passord.isBlank()) {
            throw IllegalArgumentException("Brukernavn eller passord kan ikke være tom.")
        }
    }
    constructor(bruker: Bruker) : this()
}
