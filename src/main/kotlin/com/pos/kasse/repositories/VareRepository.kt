package com.pos.kasse.repositories

import com.pos.kasse.entities.Vare
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VareRepository : CrudRepository<Vare, Long> {
}