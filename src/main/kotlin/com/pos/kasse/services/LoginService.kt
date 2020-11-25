package com.pos.kasse.services

import com.pos.kasse.entities.Bruker
import com.pos.kasse.repositories.LoginRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class LoginService(private val loginRepo: LoginRepository) : ImplLoginService {

    private val bcryptPassword: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun kontrollerBrukernavn(brukernavn: String): Boolean {
        return loginRepo.existsById(brukernavn)
    }
    override fun kontrollerLogin(bruker : Bruker): Boolean {
        val hashedPassord: String = bruker.passord
        val dbBruker: Bruker = loginRepo.findById(bruker.brukernavn).orElseThrow()
        return bcryptPassword.matches(hashedPassord, dbBruker.passord)
    }
    override fun kontrollerAdminLogin(bruker : Bruker): Boolean {
        val hashedPassord: String = bruker.passord
        val dbBruker: Bruker = loginRepo.findById(bruker.brukernavn).orElseThrow()
        return bcryptPassword.matches(hashedPassord, dbBruker.passord) && dbBruker.privilege
    }

    override fun lagNyBruker(bruker: Bruker) {
        loginRepo.save(bruker)
    }
}