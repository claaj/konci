package data.format

import data.process.intToProvinciaString
import data.process.stringToLocalDate
import kotlinx.datetime.LocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import java.nio.file.Path

private fun convenioMultiPercepciones(path: Path): DataFrame<*> {
    val columnaProvincia = mutableListOf<String>()
    val columnaCuit = mutableListOf<String>()
    val columnaFecha = mutableListOf<LocalDate>()
    val columnaComprobante = mutableListOf<String>()
    val columnaImporte = mutableListOf<Double>()

    path.toFile().forEachLine { linea ->
        val codigoProvincia = linea.slice(0..2).toInt()
        val codigoProvinciaString = intToProvinciaString(codigoProvincia)
        val cuit = linea.slice(3..15).replace("-", "")
        val fecha = stringToLocalDate(linea.slice(16..25))
        val comprobante = linea.slice(26..37)
        val importe = linea.slice(40..<linea.length).replace(",", ".").toDouble()
        columnaProvincia.add(codigoProvinciaString)
        columnaCuit.add(cuit)
        columnaFecha.add(fecha)
        columnaComprobante.add(comprobante)
        columnaImporte.add(importe)
    }

    return dataFrameOf(
        Pair("CUIT", columnaCuit),
        Pair("N_COMP", columnaComprobante),
        Pair("FECHA", columnaFecha),
        Pair("IMPORTE", columnaImporte),
        Pair("PROVINCIA", columnaProvincia),
    ).add("ORIGEN") { "ConvenioMulti" }
}

private fun convenioMultiRetenciones(path: Path): DataFrame<*> {
    val columnaProvincia = mutableListOf<String>()
    val columnaCuit = mutableListOf<String>()
    val columnaFecha = mutableListOf<LocalDate>()
    val columnaComprobante = mutableListOf<String>()
    val columnaImporte = mutableListOf<Double>()

    path.toFile().forEachLine { linea ->
        val codigoProvincia = linea.slice(0..2).toInt()
        val codigoProvinciaString = intToProvinciaString(codigoProvincia)
        val cuit = linea.slice(3..15).replace("-", "")
        val fecha = stringToLocalDate(linea.slice(16..25))
        val comprobante = linea.slice(26..45)
        val importe = linea.slice(69..<linea.length).replace(",", ".").toDouble()
        columnaProvincia.add(codigoProvinciaString)
        columnaCuit.add(cuit)
        columnaFecha.add(fecha)
        columnaComprobante.add(comprobante)
        columnaImporte.add(importe)
    }
    return dataFrameOf(
        Pair("CUIT", columnaCuit),
        Pair("N_COMP", columnaComprobante),
        Pair("FECHA", columnaFecha),
        Pair("IMPORTE", columnaImporte),
        Pair("PROVINCIA", columnaProvincia),
    ).add("ORIGEN") { "ConvenioMulti" }
}

fun dfConvenioMultiPercepciones(paths: List<Path>): DataFrame<*> {
    return pathsToDataframe(paths) {
        convenioMultiPercepciones(it)
    }
}

fun dfConvenioMultiRetenciones(rutas: List<Path>): DataFrame<*> {
    return pathsToDataframe(rutas) {
        convenioMultiRetenciones(it)
    }
}
