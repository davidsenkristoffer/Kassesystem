package com.pos.kasse.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bruker", schema = "varer")
data class Bruker constructor(
        @Id
        @Column(name = "brukernavn")
        private val brukernavn: String,
        @Column(name = "passord")
        private val passord: String
)