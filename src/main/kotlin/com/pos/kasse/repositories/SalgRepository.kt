package com.pos.kasse.repositories

import com.pos.kasse.entities.Salg
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SalgRepository : CrudRepository<Salg, Int>