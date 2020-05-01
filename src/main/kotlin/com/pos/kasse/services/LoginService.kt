package com.pos.kasse.services

import com.pos.kasse.entities.Bruker
import com.pos.kasse.repositories.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class LoginService : ImplLoginService {

    @Autowired
    private lateinit var loginRepo: LoginRepository

    private val bcryptPassword: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun kontrollerBrukernavn(brukernavn: String): Boolean {
        return loginRepo.existsById(brukernavn)
    }

    override fun kontrollerLogin(bruker : Bruker, hashedPassord: String): Boolean {
        val dbBruker: Bruker = loginRepo.findById(bruker.brukernavn).orElseThrow()
        return bcryptPassword.matches(bruker.passord, dbBruker.passord)
    }

    override fun lagNyBruker(bruker: Bruker) {
        loginRepo.save(bruker)
    }
}