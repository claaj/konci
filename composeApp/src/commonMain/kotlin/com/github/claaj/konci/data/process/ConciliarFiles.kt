package com.github.claaj.konci.data.process

import com.github.claaj.konci.data.format.dfAfip
import com.github.claaj.konci.data.format.dfSuss
import com.github.claaj.konci.data.format.dfConvenioMultiPercepciones
import com.github.claaj.konci.data.format.dfConvenioMultiRetenciones
import com.github.claaj.konci.data.format.dfTangoIIBBPercepciones
import com.github.claaj.konci.data.format.dfTangoIIBBRetenciones
import com.github.claaj.konci.data.format.dfTangoPercepciones
import com.github.claaj.konci.data.format.dfTangoRetenciones
import com.github.claaj.konci.enums.Impuestos
import com.github.claaj.konci.enums.Regimen
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.head
import java.nio.file.Path
import kotlin.math.absoluteValue

fun conciliarFiles(
    pathsExternal: List<Path>,
    pathsLocal: List<Path>,
    regimen: Regimen,
    impuesto: Impuestos
): DataFrame<*> {
    val (formatExternal, formatLocal) = getFormatFunctions(regimen, impuesto)
    val dfExternal = formatExternal(pathsExternal)
    val dfLocal = formatLocal(pathsLocal)
    val columns = getDataFrameColumns(impuesto)
    var dfFilter = dataFrameOf(columns, listOf())

    val uniqueCuits = uniqueCuits(dfExternal, dfLocal)

    for (cuit in uniqueCuits) {
        if (!checkImportesSum(dfExternal, dfLocal, cuit)) {
            for (importe in uniqueImportesByCuit(dfExternal, dfLocal, cuit)) {
                val afipFilter = filterByCuitImporte(dfExternal, cuit, importe)
                val tangoFilter = filterByCuitImporte(dfLocal, cuit, importe)

                val countDiff = afipFilter.count() - tangoFilter.count()
                if (countDiff > 0) {
                    dfFilter = dfFilter.concat(afipFilter.head(countDiff.absoluteValue))
                }
                if (countDiff < 0) {
                    dfFilter = dfFilter.concat(tangoFilter.head(countDiff.absoluteValue))
                }
            }
        }
    }
    return dfFilter
}

private fun getDataFrameColumns(impuesto: Impuestos): List<String> {
    return when (impuesto) {
        Impuestos.IIBB -> listOf("CUIT", "N_COMP", "FECHA", "IMPORTE", "PROVINCIA", "ORIGEN")
        else -> listOf("CUIT", "RAZON_SOC", "N_COMP", "FECHA", "IMPORTE", "ORIGEN")
    }
}

private fun getFormatFunctions(
    regimen: Regimen,
    impuesto: Impuestos
): Pair<(List<Path>) -> DataFrame<*>, (List<Path>) -> DataFrame<*>> {
    return when(regimen) {
        Regimen.Percepciones -> {
            when(impuesto) {
                Impuestos.Ganancias -> Pair(::dfAfip, ::dfTangoPercepciones)
                Impuestos.IIBB -> Pair(::dfConvenioMultiPercepciones, ::dfTangoIIBBPercepciones)
                Impuestos.IVA -> Pair(::dfAfip, ::dfTangoPercepciones)
                Impuestos.SUSS -> Pair(::dfSuss, ::dfTangoPercepciones)
            }
        }

        Regimen.Retenciones -> {
            when(impuesto) {
                Impuestos.Ganancias -> Pair(::dfAfip, ::dfTangoRetenciones)
                Impuestos.IIBB -> Pair(::dfConvenioMultiRetenciones, ::dfTangoIIBBRetenciones)
                Impuestos.IVA -> Pair(::dfAfip, ::dfTangoRetenciones)
                Impuestos.SUSS -> Pair(::dfSuss, ::dfTangoRetenciones)
            }
        }

        Regimen.Ambos -> throw UnsupportedOperationException()
    }
}
