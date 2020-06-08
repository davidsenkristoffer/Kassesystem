package com.pos.kasse.views

import com.pos.kasse.controllers.SalesController
import com.pos.kasse.controllers.SubtotalStatus
import com.pos.kasse.entities.Vare
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.MainWindowStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleLongProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.util.Duration
import tornadofx.*

class MainWindow : View() {
    private val logger = Logger()
    private val salescontroller: SalesController by inject()

    //plu eller ean
    private var ean: Long = 0
    private val eanProperty = SimpleLongProperty(this, "", ean)

    //Subtotal
    private val vmsub: SubtotalStatus by inject()

    override val root = borderpane {
        center {
            vbox {
                /*
                TODO: Første input registreres ikke.
                TODO: scrollTo() i tableview skal alltid scrolle til nederste objekt.
                 */
                tableview(salescontroller.observablelist) {
                    readonlyColumn("Navn", Vare::navn).sortType
                    readonlyColumn("Pris", Vare::pris).sortType
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
                        if (salescontroller.checkEanInputLength(this.length)) {
                            logger.alertOnMain("For mange tall!")
                            this.clear()
                        }
                        if (it.code == KeyCode.ENTER) {
                            this.stripNonInteger()
                            this.bind(eanProperty)
                            ean = eanProperty.get()
                            if (this.length == 3 || this.length == 4) {
                                salescontroller.addPluToTable(ean)
                            }
                            if (this.length == 13) {
                                salescontroller.addEanToTable(ean)
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
            hbox {
                button("Bank") {
                    setOnAction {
                        if (salescontroller.checkItems()) {
                            this@MainWindow.replaceWith(PaymentView::class,
                                    transition = ViewTransition.FadeThrough(1.seconds, Color.TRANSPARENT))
                        }
                    }
                }
                button("Kontant") {
                    setOnAction { logger.alertOnMain("Datamaskinen tar ikke kontant... enda.") }
                }
                button("Bong Journal") {
                    setOnAction {
                        this@MainWindow.replaceWith(ReceiptJournalView::class,
                                transition = ViewTransition.FadeThrough(1.seconds, Color.TRANSPARENT)) }
                }
                button("Skriv kvittering") {
                    setOnAction { salescontroller.writeLastReceipt() }
                }
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

    override fun onDock() {
        salescontroller.cleanObservablelist()
        salescontroller.initNewSale()
        vmsub.lastNumber.set(0)

    }
}