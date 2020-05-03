package com.pos.kasse.services

import com.pos.kasse.TheApp
import com.pos.kasse.entities.Bruker
import com.pos.kasse.repositories.LoginRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class LoginService(private val loginRepo: LoginRepository) : ImplLoginService {

    private val bcryptPassword: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun kontrollerBrukernavn(brukernavn: String): Boolean {
        return loginRepo.existsById(brukernavn)
    }

    override fun kontrollerLogin(bruker : Bruker): Boolean {
        val hashedPassord: String = bruker.passord
        val dbBruker: Bruker = loginRepo.findById(bruker.brukernavn).orElseThrow()
        //return bcryptPassword.matches(hashedPassord, dbBruker.passord)
        return hashedPassord == dbBruker.passord
    }

    override fun lagNyBruker(bruker: Bruker) {
        loginRepo.save(bruker)
    }
}