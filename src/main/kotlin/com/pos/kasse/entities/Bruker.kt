package com.pos.kasse.entities

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bruker", schema = "varer")
data class Bruker(
        @Id
        var brukernavn: String = "",
        var passord: String = "",
        var privilege: Boolean = false
) : Serializable {
    constructor(bruker: Bruker) : this()
}
