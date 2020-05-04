package com.pos.kasse.services

import com.pos.kasse.entities.Kvittering

interface ImplKvitteringService {

    fun leggTilKvittering(kvittering: Kvittering)

    fun finnKvittering(id: Int): Kvittering

}