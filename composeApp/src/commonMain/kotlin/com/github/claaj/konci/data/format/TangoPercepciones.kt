package com.github.claaj.konci.data.format

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

private fun tangoPercepciones(path: Path): DataFrame<*> {
    return DataFrame.readExcel(path.toString())
        .select {
            col("CUIT") and col("RAZON_SOC") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE")

        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("FECH_COMP").into("FECHA")
        .convert("IMPORTE").toDouble()
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

fun dfTangoPercepciones(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        tangoPercepciones(it)
    }
}
