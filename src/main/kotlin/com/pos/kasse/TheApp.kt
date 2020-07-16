package com.pos.kasse

import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.styles.MainWindowStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.views.LoginView
import javafx.stage.Stage
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import tornadofx.*
import kotlin.reflect.KClass

/**
 * Ikke bruk @Table fra Spring, da blir det rot.
 */

@SpringBootApplication
class TheApp: App() {
    override val primaryView = LoginView::class
    private lateinit var context: ConfigurableApplicationContext

    init {
        importStylesheet(Navbar::class)
        importStylesheet(LoginStyle::class)
        importStylesheet(Footer::class)
        importStylesheet(MainWindowStyle::class)
    }

    override fun start(stage: Stage) {
        try {
            super.start(stage)
            stage.width = 1000.0
            stage.height = 600.0
        } catch (e: Exception) {
            e.message
        }
    }

    override fun init() {
        this.context = SpringApplication.run(this.javaClass)
        context.autowireCapableBeanFactory.autowireBean(this)
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = context.getBean(type.java)
        }
    }

    override fun stop() {
        super.stop()
        context.close()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TheApp::class.java, *args)
        }
    }
}