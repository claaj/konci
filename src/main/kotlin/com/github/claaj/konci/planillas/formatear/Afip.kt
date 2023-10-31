package com.github.claaj.konci.planillas.formatear

import com.github.claaj.konci.planillas.proceso.cuitAfipCastAString
import com.github.claaj.konci.planillas.proceso.stringToLocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

internal fun tablaAfip(ruta: Path): DataFrame<*> {
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
        .convert("CUIT").with { cuitAfipCastAString(it) }
        .convert { "FECHA"<String>() }.with { stringToLocalDate(it) }
    return df
}

fun tablasAfip(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaAfip(it)
    }
}