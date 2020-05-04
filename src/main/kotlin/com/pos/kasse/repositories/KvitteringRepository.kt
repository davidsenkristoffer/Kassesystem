package com.pos.kasse.repositories

import com.pos.kasse.entities.Kvittering
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KvitteringRepository : CrudRepository<Kvittering, Int> {
}