package com.pos.kasse.entities

import org.springframework.data.relational.core.mapping.Table
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Table
data class Bruker(
        @Id
        var brukernavn: String = "",
        var passord: String = ""
) {
    constructor(bruker: Bruker) : this()
}
