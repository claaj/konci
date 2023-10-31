package com.github.claaj.konci.planillas.formatear

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

internal fun tablaTango(ruta: Path): DataFrame<*> {
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

fun tablasTango(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaTango(it)
    }
}
