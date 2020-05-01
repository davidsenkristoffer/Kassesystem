package com.pos.kasse

import com.pos.kasse.styles.Navbar
import com.pos.kasse.views.Login
import com.pos.kasse.views.Varesoek
import javafx.application.Application
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.reloadStylesheetsOnFocus
import kotlin.reflect.KClass

@SpringBootApplication
class TheApp: App(Login::class, Navbar::class) {

    private lateinit var context: ConfigurableApplicationContext

    override fun init() {
        this.context = SpringApplication.run(this.javaClass)
        context.autowireCapableBeanFactory.autowireBean(this)
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = context.getBean(type.java)
        }
        reloadStylesheetsOnFocus()
        /**
         * Important: For the reload to work, you must be running the JVM in debug mode
         * and you must instruct your IDE to recompile before you switch back to your app.
         * Without these steps, nothing will happen.
         * This also applies to reloadViewsOnFocus() which is similar,
         * but reloads the whole view instead of just the stylesheet.
         * This way, you can evolve your UI very rapidly in a "code change, compile, refresh" manner.
         */
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