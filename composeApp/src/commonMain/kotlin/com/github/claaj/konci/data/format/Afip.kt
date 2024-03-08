package com.github.claaj.konci.data.format

import com.github.claaj.konci.data.process.cuitAfiptoString
import com.github.claaj.konci.data.process.stringToLocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.readExcel
import java.nio.file.Path

private fun afip(path: Path): DataFrame<*> {
    return DataFrame.readExcel(path.toString())
        .select {
            col("CUIT Agente Ret./Perc.") and col("Denominación o Razón Social") and col("Número Comprobante") and col("Fecha Comprobante") and col(
                "Importe Ret./Perc."
            )
        }
        .dropNulls()
        .rename("CUIT Agente Ret./Perc.").into("CUIT")
        .rename("Denominación o Razón Social").into("RAZON_SOC")
        .rename("Importe Ret./Perc.").into("IMPORTE")
        .rename("Número Comprobante").into("N_COMP")
        .rename("Fecha Comprobante").into("FECHA")
        .add("ORIGEN") { "AFIP" }
        .convert("IMPORTE").toDouble()
        .convert("CUIT").with { cuitAfiptoString(it) }
        .convert { "FECHA"<String>() }.with { stringToLocalDate(it) }
}

fun dfAfip(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        afip(it)
    }
}
