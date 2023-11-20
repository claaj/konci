package com.github.claaj.konci.planillas.formatear

import com.github.claaj.konci.planillas.proceso.convertirNumeroAProvincia
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

internal fun tablaTangoIIBBRet(ruta: Path): DataFrame<*> {
    return DataFrame.readExcel(ruta.toString())
        .select {
            col("IDENTIFTRI") and col("N_COMP") and col("FECHA") and col("IMP_RET") and ("JURISDIC")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("IMP_RET").into("IMPORTE")
        .rename("IDENTIFTRI").into("CUIT")
        .rename("JURISDIC").into("PROVINCIA")
        .convert { "PROVINCIA"<String>() }.with { convertirNumeroAProvincia(it.toInt()) }
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

internal fun tablaTangoIIBBPercep(ruta: Path): DataFrame<*> {
    return DataFrame.readExcel(ruta.toString())
        .select {
            col("CUIT") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE") and ("JURISDIC")
        }
        .dropNulls()
        .add("ORIGEN") { "TANGO" }
        .rename("JURISDIC").into("PROVINCIA")
        .rename("FECH_COMP").into("FECHA")
        .convert { "PROVINCIA"<String>() }.with { convertirNumeroAProvincia(it.toInt()) }
        .convert { "FECHA"<LocalDateTime>() }.with { LocalDate(it.year, it.monthNumber, it.dayOfMonth) }
        .convert { "CUIT"<String>() }.with { it.replace("-", "") }
}

fun tablasTangoIIBBRet(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaTangoIIBBRet(it)
    }
}

fun tablasTangoIIBBPercep(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaTangoIIBBPercep(it)
    }
}

