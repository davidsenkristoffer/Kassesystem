package com.pos.kasse.services

import com.pos.kasse.entities.Kvittering

interface ImplKvitteringService {

    fun leggTilKvittering(kvittering: Kvittering): Kvittering

    fun finnKvittering(id: Int): Kvittering

    fun hentAlleKvitteringer(): MutableIterable<Kvittering>

}