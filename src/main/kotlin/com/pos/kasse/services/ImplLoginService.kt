package com.pos.kasse.services

import com.pos.kasse.entities.Bruker

interface ImplLoginService {

    fun kontrollerBrukernavn(brukernavn : String) : Boolean

    fun kontrollerLogin(bruker : Bruker, hashedPassord : String) : Boolean

    fun lagNyBruker(bruker : Bruker)
}