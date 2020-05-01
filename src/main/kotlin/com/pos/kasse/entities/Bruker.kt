package com.pos.kasse.entities

import org.springframework.stereotype.Component
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bruker", schema = "varer")
data class Bruker(
        @Id
        val brukernavn: String = "",
        val passord: String = ""
) {
    init {
        if (brukernavn.isBlank() || passord.isBlank()) {
            throw IllegalArgumentException("Brukernavn eller passord kan ikke være tom.")
        }
    }
    constructor(bruker: Bruker) : this()
}
