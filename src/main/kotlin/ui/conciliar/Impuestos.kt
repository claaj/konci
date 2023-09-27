package ui.conciliar

import dataframe.setupTablasAfip
import dataframe.setupTablasTango
import org.jetbrains.kotlinx.dataframe.DataFrame
import ui.conciliar.tabs.TabsConciliarEstado
import java.nio.file.Path

enum class Impuestos(
    val titulo: String,
    val tabsConciliar: TabsConciliarEstado,
    val setupExternos: (List<Path>) -> DataFrame<*>,
    val setupLocales: (List<Path>) -> DataFrame<*>,
) {
    IVA(
        "IVA",
        TabsConciliarEstado("AFIP", "Tango", "Conciliar"),
        ::setupTablasAfip,
        ::setupTablasTango
    ),
    GANANCIAS(
        "Ganancias",
        TabsConciliarEstado("AFIP", "Tango", "Conciliar"),
        ::setupTablasAfip,
        ::setupTablasTango
    ),
}
