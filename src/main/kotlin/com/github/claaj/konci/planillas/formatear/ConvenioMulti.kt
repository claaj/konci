package com.github.claaj.konci.planillas.formatear

import com.github.claaj.konci.planillas.proceso.convertirNumeroAProvincia
import com.github.claaj.konci.planillas.proceso.stringToLocalDate
import kotlinx.datetime.LocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import java.nio.file.Path

internal fun tablaConvenioMultiPercep(ruta: Path): DataFrame<*> {
    val columnaProvincia = mutableListOf<String>()
    val columnaCuit = mutableListOf<String>()
    val columnaFecha = mutableListOf<LocalDate>()
    val columnaComprobante = mutableListOf<String>()
    val columnaImporte = mutableListOf<Double>()

    ruta.toFile().forEachLine { linea ->
        val codigoProvincia = linea.slice(0..2).toInt()
        val codigoProvinciaString = convertirNumeroAProvincia(codigoProvincia)
        val cuit = linea.slice(3..15).replace("-", "")
        val fecha = stringToLocalDate(linea.slice(16..25))
        val comprobante = linea.slice(26..39)
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

internal fun tablaConvenioMultiReten(ruta: Path): DataFrame<*> {
    val columnaProvincia = mutableListOf<String>()
    val columnaCuit = mutableListOf<String>()
    val columnaFecha = mutableListOf<LocalDate>()
    val columnaComprobante = mutableListOf<String>()
    val columnaImporte = mutableListOf<Double>()

    ruta.toFile().forEachLine { linea ->
        val codigoProvincia = linea.slice(0..2).toInt()
        val codigoProvinciaString = convertirNumeroAProvincia(codigoProvincia)
        val cuit = linea.slice(3..15).replace("-", "")
        val fecha = stringToLocalDate(linea.slice(16..25))
        val comprobante = linea.slice(26..39)
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

fun tablasConvenioMultiPercep(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaConvenioMultiPercep(it)
    }
}

fun tablasConvenioMultiReten(rutas: List<Path>): DataFrame<*> {
    return rutasADataframe(rutas) {
        tablaConvenioMultiReten(it)
    }
}