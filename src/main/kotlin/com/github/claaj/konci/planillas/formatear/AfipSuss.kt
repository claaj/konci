package com.github.claaj.konci.planillas.formatear

import com.github.claaj.konci.planillas.proceso.cuitAfipCastAString
import com.github.claaj.konci.planillas.proceso.stringToLocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

internal fun tablaSuss(ruta: Path): DataFrame<*> {
    return DataFrame.readExcel(ruta.toString())
        .select {
            col("CUIT Agente Ret./Perc.") and col("Denominación o Razón Social") and col("Número Certificado") and col("Fecha Ret./Perc.") and col(
                "Importe Ret./Perc."
            )
        }
        .dropNulls()
        .rename("CUIT Agente Ret./Perc.").into("CUIT")
        .rename("Denominación o Razón Social").into("RAZON_SOC")
        .rename("Importe Ret./Perc.").into("IMPORTE")
        .rename("Número Certificado").into("N_COMP")
        .rename("Fecha Ret./Perc.").into("FECHA")
        .add("ORIGEN") { "AFIP" }
        .convert("IMPORTE").toDouble()
        .convert("CUIT").with { cuitAfipCastAString(it) }
        .convert { "FECHA"<String>() }.with { stringToLocalDate(it) }
}

fun tablasSuss(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaSuss(it)
    }
}