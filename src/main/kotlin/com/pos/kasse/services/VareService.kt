package com.pos.kasse.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.repositories.VareRepository
import org.springframework.stereotype.Component

@Component
class VareService(private val vareRepository: VareRepository) : ImplVareService {

    override fun hentAlleVarer(): List<Vare> {
        return vareRepository.findAll().toList()
    }
    override fun finnVareMedId(ean: Long): Vare {
        return vareRepository.findById(ean).orElseThrow()
    }
    override fun hentVarerIKategori(enum: Enum<Kategori>, liste: List<Vare>): List<Vare> {
        return liste.filter { vare -> vare.kategori == enum }
    }
    override fun finnesVare(ean: Long): Boolean {
        return vareRepository.existsById(ean)
    }
    override fun leggTilVare(vare: Vare) {
        vareRepository.save(vare)
    }
    override fun slettVare(vare: Vare) {
        vareRepository.delete(vare)
    }
    override fun oppdaterVare(vare: Vare) {
        vareRepository.save(vare)
    }
    override fun leggTilAlleVarer(liste: MutableIterable<Vare>): MutableIterable<Vare> {
        return vareRepository.saveAll(liste)
    }
}