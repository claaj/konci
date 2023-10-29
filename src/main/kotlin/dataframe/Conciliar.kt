package dataframe

import dataframe.errores.FormatoInvalidoException
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path
import kotlin.math.absoluteValue

val COLUMNAS = listOf("CUIT", "RAZON_SOC", "N_COMP", "FECHA", "IMPORTE")

fun conciliar(
    archivosExternos: List<Path>,
    archivosLocales: List<Path>,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>
): DataFrame<*> {
    val dfExterno = setupExternos(archivosExternos)
    val dfLocal = setupLocales(archivosLocales)
    var dfFiltrado = dataFrameOf(COLUMNAS, listOf())

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

private fun setupTablaAfip(ruta: Path): DataFrame<*> {
    val df = DataFrame.readExcel(ruta.toString())
        .select {
            col("CUIT Agente Ret./Perc.") and col("Denominación o Razón Social") and col("Número Comprobante") and col("Fecha Comprobante") and col(
                "Importe Ret./Perc."
            )
        }
        .rename("CUIT Agente Ret./Perc.").into("CUIT")
        .rename("Denominación o Razón Social").into("RAZON_SOC")
        .rename("Importe Ret./Perc.").into("IMPORTE")
        .rename("Número Comprobante").into("N_COMP")
        .rename("Fecha Comprobante").into("FECHA")
        .add("ORIGEN") { "AFIP" }
        .convert("CUIT").with { it.toString() }
        .convert { "FECHA"<String>() }.with { stringToLocalDate(it) }
    return df
}

private fun setupTablaTango(ruta: Path): DataFrame<*> {
    val df = DataFrame.readExcel(ruta.toString())
        .select {
            col("CUIT") and col("RAZON_SOC") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("FECH_COMP").into("FECHA")
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
    return df
}

fun setupTablasAfip(rutas: List<Path>): DataFrame<*> {
    val tablas = mutableListOf<DataFrame<*>>()

    for (ruta in rutas) {
        try {
            val rutaProcesada = setupTablaAfip(ruta)
            tablas.add(rutaProcesada)
        } catch (_: IllegalStateException) {
            throw FormatoInvalidoException(ruta.toString())
        }
    }
    return tablas.concat()
}

fun setupTablasTango(rutas: List<Path>): DataFrame<*> {
    val tablas = mutableListOf<DataFrame<*>>()

    for (ruta in rutas) {
        try {
            val rutaProcesada = setupTablaTango(ruta)
            tablas.add(rutaProcesada)
        } catch (_: IllegalStateException) {
            throw FormatoInvalidoException(ruta.toString())
        }
    }
    return tablas.concat()
}
