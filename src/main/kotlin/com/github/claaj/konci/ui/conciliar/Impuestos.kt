package com.github.claaj.konci.ui.conciliar

import com.github.claaj.konci.planillas.formatear.tablasAfip
import com.github.claaj.konci.planillas.formatear.tablasTango
import org.jetbrains.kotlinx.dataframe.DataFrame
import java.nio.file.Path

enum class Impuestos(
    val titulo: String,
    val nombreListaExterna: String,
    val nombreListaLocal: String,
    val nombreProcesar: String,
    val formatearExternos: (List<Path>) -> DataFrame<*>,
    val formatearLocales: (List<Path>) -> DataFrame<*>,
) {
    IVA(
        "IVA",
        "AFIP",
        "Tango",
        "Conciliar",
        ::tablasAfip,
        ::tablasTango
    ),
    GANANCIAS(
        "Ganancias",
        "AFIP",
        "Tango",
        "Conciliar",
        ::tablasAfip,
        ::tablasTango
    ),
}
