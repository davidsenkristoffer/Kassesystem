package com.pos.kasse.entities

import org.springframework.stereotype.Component
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bruker", schema = "varer")
data class Bruker(
        @Id
        var brukernavn: String = "",
        var passord: String = ""
) {
    constructor(bruker: Bruker) : this()
}
