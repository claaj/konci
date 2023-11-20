package com.github.claaj.konci.planillas.formatear

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

internal fun tablaTangoRetenciones(ruta: Path): DataFrame<*> {
    return DataFrame.readExcel(ruta.toString())
        .select {
            col("IDENTIFTRI") and col("RAZ_SOC") and col("N_COMP") and col("FECHA") and col("IMP_RET")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("RAZ_SOC").into("RAZON_SOC")
        .rename("IMP_RET").into("IMPORTE")
        .rename("IDENTIFTRI").into("CUIT")
        .convert("IMPORTE").toDouble()
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

fun tablasTangoRetenciones(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaTangoRetenciones(it)
    }
}