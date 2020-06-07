package com.pos.kasse.services

import com.pos.kasse.entities.Salg
import com.pos.kasse.repositories.SalgRepository
import org.springframework.stereotype.Component

@Component
class SalgService(private val salgRepository: SalgRepository) : ImplSalgService {

    override fun leggTilSalg(salg: Salg) : Salg {
        return salgRepository.save(salg)
    }


}