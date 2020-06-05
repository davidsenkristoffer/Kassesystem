package com.pos.kasse.services

import com.pos.kasse.entities.Kvittering
import com.pos.kasse.repositories.KvitteringRepository
import org.springframework.stereotype.Component

@Component
class KvitteringService(private val kvitteringRepository: KvitteringRepository) : ImplKvitteringService {

    override fun leggTilKvittering(kvittering: Kvittering): Kvittering {
        return kvitteringRepository.save(kvittering)
    }

    override fun finnKvittering(id: Int): Kvittering {
        return kvitteringRepository.findById(id).orElseThrow()
    }


}