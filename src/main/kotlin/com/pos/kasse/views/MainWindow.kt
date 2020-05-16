package com.pos.kasse.views

import com.pos.kasse.entities.Kategori
import com.pos.kasse.entities.Vare
import com.pos.kasse.services.VareService
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.MainWindowStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.util.Duration
import tornadofx.*

class MainWindow : View() {
    //TODO: Serialisere objekter før henting av databaseobjekter er mulig.
    private val vareService: VareService by di()
    private val logger = Logger()

    //Liste med alle varer
    private val vareliste = mutableListOf(
            Vare(1234567890123, "drue", 10, "blå drue",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890124, "appelsin", 10, "oransje appelsin",
            700, "F9", Kategori.FRUKT),
            Vare(1234567890125, "eple", 10, "gul eple",
            722, "F9", Kategori.FRUKT),
            Vare(1234567890126, "poteter", 22, "brune poteter",
            743, "F9", Kategori.FRUKT),
            Vare(1234567890128, "banan", 35, "gul banan",
            710, "F9", Kategori.FRUKT),
            Vare(1234567890129, "gulrøtter", 21, "oransje gulrøtter",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890130, "ruccula", 19, "grønn ruccula",
            null, "F9", Kategori.FRUKT),
            Vare(1234567890147, "smoothie", 10, "blå smoothie",
                    null, "F9", Kategori.FRUKT)
            ).asObservable()

    //Liste der man legger til varer i salget, pr. nå ved å trykke på knappen.
    private var observablelist = mutableListOf<Vare>().asObservable()
    private var index = 0

    //plu eller ean
    private var ean: Long = 0
    private val eanProperty = SimpleLongProperty(this, "", ean)
    private var plu: Int = 0
    private val pluProperty = SimpleIntegerProperty(this, "", plu)

    //Subtotal
    private val vm: SubtotalAppend by inject()
    private val vmsub: SubtotalStatus by inject()

    override val root = borderpane {
        center {
            vbox {
                /*
                TODO: Første input registreres ikke.
                TODO: Fikse ean-innlesing
                TODO: scrollTo() i tableview skal alltid scrolle til nederste objekt.
                 */
                tableview(observablelist) {
                    readonlyColumn("Navn", Vare::navn).sortType
                    readonlyColumn("Pris", Vare::pris).sortType

                    //TODO()
                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            scrollTo(observablelist.lastIndex)
                        }
                    }
                    columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
                }
                hbox {
                    label("Subtotal: ")
                    label(vmsub.lastNumber)
                    alignment = Pos.CENTER_RIGHT
                    padding = Insets(2.0,10.0,2.0,5.0)
                }
                textfield {
                    setOnKeyPressed {
                        if (it.code == KeyCode.ENTER) {
                            if (this.length == 3 || this.length == 4) { //PLU kan være 3 eller 4 karakterer
                                this.bind(pluProperty)
                                plu = pluProperty.get()
                                vareliste.forEach { vare ->
                                    if (vare.plu == plu) {
                                        observablelist.add(vare)
                                        vm.save(vare.pris)
                                    }
                                }
                            }
                            runLater(Duration.ONE) {
                                this.clear()
                            }
                        }
                        if (it.code == KeyCode.BACK_SPACE) {
                            this.clear()
                        }
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

//Legger til pris ved bruk av FXEvent.
class SubtotalAppend : ViewModel() {
    val data = SimpleIntegerProperty()
    fun save(pris: Int) {
        println("Pris = $pris")
        fire(DataSavedEvent(pris))
    }
}

//Viser subtotal i View.
class SubtotalStatus : ViewModel() {
    val lastNumber = SimpleIntegerProperty()
    init {
        subscribe<DataSavedEvent> {
            lastNumber.value += it.message
            println(lastNumber.value)
        }
    }
}

//Event som holder siste pris
class DataSavedEvent(val message: Int) : FXEvent()