package com.pos.kasse.views

import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.Navbar
import javafx.scene.control.TableView
import tornadofx.*

class MainWindow : View() {

    private val vareService: VareService by di()
    //TODO: Serialiser objekter
    //private val dbvareliste = vareService.hentAlleVarer().toList()

    //Liste med alle varer
    val vareliste = mutableListOf(
            Vare(1234567890123, "drue", 10, "blå drue",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890124, "appelsin", 10, "oransje appelsin",
            700, "F9", Kategori.FRUKT),
            Vare(1234567890125, "eple", 10, "gul eple",
            722, "F9", Kategori.FRUKT),
            Vare(1234567890126, "poteter", 10, "brune poteter",
            743, "F9", Kategori.FRUKT),
            Vare(1234567890127, "banan", 10, "gul banan",
            710, "F9", Kategori.FRUKT)
            ).asObservable()

    //Liste der man legger til varer i salget, pr. nå ved å trykke på knappen.
    val observablelist = mutableListOf<Vare>().asObservable()
    var index = 0

    override val root = borderpane {
        center {
            vbox {
                tableview(observablelist) {
                    column("Navn", Vare::navn)
                    column("Pris", Vare::pris)
                    columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                }
                button("press for adding line...") {
                    setOnAction {
                        observablelist.add(vareliste[index])
                        index++
                    }
                }
            }
        }
        top {
            label("Davidsens matkolonial") {
                addClass(Navbar.wrapper)
            }
        }
        bottom {
            label("bunntekst, knapper osv") {
                addClass(Footer.wrapper)
            }
        }
        right {
        }
        left {
        }
    }
}