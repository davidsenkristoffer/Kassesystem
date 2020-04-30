package com.pos.kasse.repositories

import com.pos.kasse.entity.Bruker
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LoginRepository : CrudRepository<Bruker, String>