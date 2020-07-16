package com.pos.kasse.services

import com.pos.kasse.entities.Bruker

interface ImplLoginService {

    fun kontrollerBrukernavn(brukernavn : String) : Boolean

    fun kontrollerLogin(bruker : Bruker) : Boolean

    fun lagNyBruker(bruker : Bruker)

    fun kontrollerAdminLogin(bruker : Bruker) : Boolean
}