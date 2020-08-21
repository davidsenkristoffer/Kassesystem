package com.pos.kasse.views

import com.pos.kasse.controllers.ItemSearchController
import com.pos.kasse.entities.Vare
import javafx.beans.property.ReadOnlyListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.ObservableList
import tornadofx.*

class ItemSearchView : View() {

    private val itemSearchController: ItemSearchController by inject()

    private var searchstring: String = ""
    private val stringProp = SimpleStringProperty(this, "", searchstring)

    override val root = borderpane {

        center {
            vbox {
                tableview(itemSearchController.itemList as ObservableList<Vare>) {
                    readonlyColumn("EAN", Vare::ean) { sortOrder.add(this) }
                    readonlyColumn("PLU", Vare::plu)
                    readonlyColumn("Navn", Vare::navn)
                    readonlyColumn("Pris", Vare::pris)
                }
                textfield {
                    setOnKeyPressed {
                        this.bind(stringProp)
                        runAsync {
                            itemSearchController.searchAsync(stringProp.get())
                        }
                    }
                }
            }
        }

    }
    /**
     * TableView
     */

}