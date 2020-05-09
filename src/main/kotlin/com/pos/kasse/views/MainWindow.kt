package com.pos.kasse.views

import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.MainWindowStyle
import com.pos.kasse.styles.Navbar
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.scene.control.TableView
import tornadofx.*

class MainWindow : View() {
    //TODO: Serialiser objekter før henting av databaseobjekter er mulig.
    private val vareService: VareService by di()

    //Liste med alle varer
    private val vareliste = mutableListOf(
            Vare(1234567890123, "drue", 10, "blå drue",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890124, "appelsin", 10, "oransje appelsin",
            700, "F9", Kategori.FRUKT),
            Vare(1234567890125, "eple", 10, "gul eple",
            722, "F9", Kategori.FRUKT),
            Vare(1234567890126, "poteter", 10, "brune poteter",
            743, "F9", Kategori.FRUKT),
            Vare(1234567890128, "banan", 10, "gul banan",
            710, "F9", Kategori.FRUKT),
            Vare(1234567890129, "gulrøtter", 10, "oransje gulrøtter",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890130, "ruccula", 10, "grønn ruccula",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890147, "smoothie", 10, "blå smoothie",
                    null, "F9", Kategori.FRUKT)
            ).asObservable()

    //Liste der man legger til varer i salget, pr. nå ved å trykke på knappen.
    private var observablelist = mutableListOf<Vare>().asObservable()
    private var index = 0

    //subtotal
    private var subtotalsum: Int = 0
    private val subtotalProperty = SimpleIntegerProperty(this, "", subtotalsum)

    //plu eller ean
    private var pluEan: Long = 0
    private val pluEanProperty = SimpleLongProperty(this, "", pluEan)

    override val root = borderpane {

        center {
            vbox {

                /*
                TODO: Første input registreres ikke. Andre input går som normalt, legger til vare med plu.
                TODO: Fikse subtotal-prop. Legge inn som subcell i tableview?
                 */

                tableview(observablelist) {
                    readonlyColumn("Navn", Vare::navn)
                    readonlyColumn("Pris", Vare::pris)
                    columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                }
                label("Subtotal: ${subtotalProperty.value}")
                textfield {
                    filterInput { it.controlNewText.isLong() }
                    setOnAction {
                        this.bind(pluEanProperty)
                        if (this.length == 3 || this.length == 4 || this.length == 13) {
                            vareliste.forEach { vare ->
                                if (vare.plu == pluEanProperty.intValue()) {
                                    observablelist.add(vare)
                                    subtotalProperty.plus(vare.pris)
                                } else if (vare.ean == pluEanProperty.value) {
                                    observablelist.add(vare)
                                    subtotalProperty.plus(vare.pris)
                                }
                            }
                        }
                        this.clear()
                    }
                }
            }
            addClass(MainWindowStyle.tableclass)
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