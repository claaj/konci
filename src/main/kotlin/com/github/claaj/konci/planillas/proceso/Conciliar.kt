package com.github.claaj.konci.planillas.proceso

import com.github.claaj.konci.ui.conciliar.Impuesto
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.head
import java.nio.file.Path
import kotlin.math.absoluteValue

fun conciliar(
    archivosExternos: List<Path>,
    archivosLocales: List<Path>,
    formatearExternos: (List<Path>) -> DataFrame<*>,
    formatearLocales: (List<Path>) -> DataFrame<*>,
    impuesto: Impuesto
): DataFrame<*> {
    val dfExterno = formatearExternos(archivosExternos)
    val dfLocal = formatearLocales(archivosLocales)
    val columnas = obtenerFormatoTabla(impuesto)
    var dfFiltrado = dataFrameOf(columnas, listOf())

    val cuitsUnicos = cuitsUnicos(dfExterno, dfLocal)

    for (cuit in cuitsUnicos) {
        if (!mismoImporteTotal(dfExterno, dfLocal, cuit)) {
            for (importe in importesPorCuitUnicosTotal(dfExterno, dfLocal, cuit)) {
                val filtroAfip = filtroCuitImporte(dfExterno, cuit, importe)
                val filtroTango = filtroCuitImporte(dfLocal, cuit, importe)

                val diferenciaCantidad = filtroAfip.count() - filtroTango.count()
                if (diferenciaCantidad > 0) {
                    dfFiltrado = dfFiltrado.concat(filtroAfip.head(diferenciaCantidad.absoluteValue))
                }
                if (diferenciaCantidad < 0) {
                    dfFiltrado = dfFiltrado.concat(filtroTango.head(diferenciaCantidad.absoluteValue))
                }
            }
        }
    }
    return dfFiltrado
}

internal fun obtenerFormatoTabla(impuesto: Impuesto): List<String> {
    return when (impuesto) {
        Impuesto.IIBB -> listOf("CUIT", "N_COMP", "FECHA", "IMPORTE", "PROVINCIA", "ORIGEN")
        else -> listOf("CUIT", "RAZON_SOC", "N_COMP", "FECHA", "IMPORTE", "ORIGEN")
    }
}
