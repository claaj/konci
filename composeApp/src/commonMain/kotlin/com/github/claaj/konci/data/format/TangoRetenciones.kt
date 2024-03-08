package com.github.claaj.konci.data.format

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

private fun tangoRetenciones(path: Path): DataFrame<*> {
    return DataFrame.readExcel(path.toString())
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

fun dfTangoRetenciones(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        tangoRetenciones(it)
    }
}
