package com.pos.kasse.services

import com.pos.kasse.entity.Bruker
import org.springframework.stereotype.Service

interface ImplLoginService {

    fun kontrollerBrukernavn(brukernavn : String) : Boolean

    fun kontrollerLogin(bruker : Bruker, hashedPassord : String) : Boolean

    fun lagNyBruker(bruker : Bruker)
}