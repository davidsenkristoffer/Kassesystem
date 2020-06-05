package com.pos.kasse.services

import com.pos.kasse.entities.Salg

interface ImplSalgService {

    fun leggTilSalg(salg: Salg) : Salg

}