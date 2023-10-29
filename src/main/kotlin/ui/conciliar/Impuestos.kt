package ui.conciliar

import dataframe.setupTablasAfip
import dataframe.setupTablasTango
import org.jetbrains.kotlinx.dataframe.DataFrame
import java.nio.file.Path

enum class Impuestos(
    val titulo: String,
    val nombreListaExterna: String,
    val nombreListaLocal: String,
    val nombreProcesar: String,
    val setupExternos: (List<Path>) -> DataFrame<*>,
    val setupLocales: (List<Path>) -> DataFrame<*>,
) {
    IVA(
        "IVA",
        "AFIP",
        "Tango",
        "Conciliar",
        ::setupTablasAfip,
        ::setupTablasTango
    ),
    GANANCIAS(
        "Ganancias",
        "AFIP",
        "Tango",
        "Conciliar",
        ::setupTablasAfip,
        ::setupTablasTango
    ),
}
