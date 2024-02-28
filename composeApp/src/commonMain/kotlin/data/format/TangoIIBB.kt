package data.format

import data.process.intToProvinciaString
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

private fun tangoIIBBRetenciones(path: Path): DataFrame<*> {
    return DataFrame.readExcel(path.toString())
        .select {
            col("IDENTIFTRI") and col("N_COMP") and col("FECHA") and col("IMP_RET") and ("JURISDIC")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("IMP_RET").into("IMPORTE")
        .rename("IDENTIFTRI").into("CUIT")
        .rename("JURISDIC").into("PROVINCIA")
        .convert { "PROVINCIA"<String>() }.with { intToProvinciaString(it.toInt()) }
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

private fun tangoIIBBPercepciones(path: Path): DataFrame<*> {
    return DataFrame.readExcel(path.toString())
        .select {
            col("CUIT") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE") and ("JURISDIC")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("JURISDIC").into("PROVINCIA")
        .rename("FECH_COMP").into("FECHA")
        .convert { "PROVINCIA"<String>() }.with { intToProvinciaString(it.toInt()) }
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

fun dfTangoIIBBRetenciones(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        tangoIIBBRetenciones(it)
    }
}

fun dfTangoIIBBPercepciones(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        tangoIIBBPercepciones(it)
    }
}
