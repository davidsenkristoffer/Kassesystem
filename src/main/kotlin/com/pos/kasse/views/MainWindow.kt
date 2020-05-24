package com.pos.kasse.views

import com.pos.kasse.config.Runner
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
import javafx.util.Duration
import tornadofx.*

class MainWindow : View() {
    private val vareService: VareService by di()
    private val logger = Logger()
    private val startup: Runner by di()

    //Liste der man legger til varer i salget, pr. nå ved å trykke på knappen.
    private var observablelist = mutableListOf<Vare>().asObservable()
    private val varelisten = startup.vareliste.asObservable()

    //plu eller ean
    private var ean: Long = 0
    private val eanProperty = SimpleLongProperty(this, "", ean)
    private var plu: Int = 0
    private val pluProperty = SimpleIntegerProperty(this, "", plu)

    //Subtotal
    private val vm: SubtotalAppend by inject()
    private val vmsub: SubtotalStatus by inject()

    init {

    }

    override val root = borderpane {
        center {
            println(varelisten.size)
            vbox {
                /*
                TODO: Første input registreres ikke.
                TODO: Fikse ean-innlesing
                TODO: scrollTo() i tableview skal alltid scrolle til nederste objekt.
                TODO: PLU med 4 tall leses ikke. Knyttet til tekstfeltet.
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
                            if (this.length == 3 || this.length == 4) {
                                this.bind(pluProperty)
                                plu = pluProperty.get()
                                logger.printConsole("$plu") //Sjekker plu 4
                                varelisten.forEach { vare ->
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
                }.requestFocus()
            }
            addClass(MainWindowStyle.tableclass)
        }
        top {
            label("Davidsens matkolonial") {
                addClass(Navbar.wrapper)
            }
        }
        bottom {
            hbox {
                button("Bank") {}
                button("Kontant") {}
                button("Bong Journal") {}
                button("Skriv kvittering") {}
                addClass(Footer.wrapper)
            }
        }
        right {
            vbox {

            }
        }
        left {
        }
    }
}

//Legger til pris ved bruk av FXEvent.
class SubtotalAppend : ViewModel() {
    fun save(pris: Int) {
        fire(DataSavedEvent(pris))
    }
}

//Viser subtotal i View.
class SubtotalStatus : ViewModel() {
    val lastNumber = SimpleIntegerProperty()
    init {
        subscribe<DataSavedEvent> {
            lastNumber.value += it.message
        }
    }
}

//Event som holder siste pris
class DataSavedEvent(val message: Int) : FXEvent()