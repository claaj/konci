package dataframe

import kotlinx.datetime.LocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.*
import java.nio.file.Path
import kotlin.math.absoluteValue

val COLUMNAS = listOf("CUIT", "Razón Social", "Número Comprobante", "Fecha Comprobante", "Importe")

fun conciliar(
    archivosExternos: List<Path>,
    archivosLocales: List<Path>,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>
): DataFrame<*> {
    val dfExterno = setupExternos(archivosExternos)
    val dfLocal = setupLocales(archivosLocales)
    var dfFiltrado = dataFrameOf(COLUMNAS, listOf())

    val cuitsUnicos = cuitsUnicos(dfExterno, dfLocal)

    for (cuit in cuitsUnicos) {
        if (!mismoImporteTotal(dfExterno, dfLocal, cuit)) {
            for (importe in importesPorCuitUnicosTotal(dfExterno, dfLocal, cuit)) {
                val filtroAfip = filtroCuitImporte(dfExterno, cuit, importe)
                val filtroTango = filtroCuitImporte(dfLocal, cuit, importe)

                val diferenciaCantidad = filtroAfip.count() - filtroTango.count()
                if (diferenciaCantidad > 0) {
                    dfFiltrado = dfFiltrado.concat(filtroAfip.head(diferenciaCantidad.absoluteValue))
                }
                if (diferenciaCantidad < 0) {
                    dfFiltrado = dfFiltrado.concat(filtroTango.head(diferenciaCantidad.absoluteValue))
                }
            }
        }
    }
    return dfFiltrado
}

private fun setupTablaAfip(ruta: Path): DataFrame<*> {
    var df = DataFrame.readExcel(ruta.toString())

    df = df.select {
        col("CUIT Agente Ret./Perc.") and col("Denominación o Razón Social") and col("Número Comprobante") and col("Fecha Comprobante") and col(
            "Importe Ret./Perc."
        )
    }

    df = df.rename("CUIT Agente Ret./Perc.").into("CUIT")
    df = df.rename("Denominación o Razón Social").into("Razón Social")
    df = df.rename("Importe Ret./Perc.").into("Importe")
    df = df.add("Origen") { "AFIP" }
    df = df.convert("CUIT").with { it.toString() }
    df = df.convert { "Fecha Comprobante"<String>() }.with { stringToLocalDate(it) }
    return df
}

private fun setupTablaTango(ruta: Path): DataFrame<*> {
    var df = DataFrame.readExcel(ruta.toString())
    df = df.select {
        col("CUIT") and col("RAZON_SOC") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE")
    }

    df = df.rename("RAZON_SOC").into("Razón Social")
    df = df.rename("N_COMP").into("Número Comprobante")
    df = df.rename("FECH_COMP").into("Fecha Comprobante")
    df = df.rename("IMPORTE").into("Importe")
    df = df.add("Origen") { "Tango" }
    df = df.convert("Fecha Comprobante").to<LocalDate>()
    df = df.convert { "CUIT"<String>() }.with { it.replace("-", "") }
    return df
}

@Throws(IllegalStateException::class)
fun setupTablasAfip(rutas: List<Path>): DataFrame<*> {
    var df = dataFrameOf(COLUMNAS, listOf())

    rutas.forEach { ruta ->
        df = df.concat(setupTablaAfip(ruta))
    }
    return df
}

@Throws(IllegalStateException::class)
fun setupTablasTango(rutas: List<Path>): DataFrame<*> {
    var df = dataFrameOf(COLUMNAS, listOf())

    rutas.forEach { ruta ->
        df = df.concat(setupTablaTango(ruta))
    }
    return df
}
