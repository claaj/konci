package dataframe

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.*
import java.nio.file.Path
import kotlin.math.absoluteValue

val COLUMNAS = listOf("CUIT", "RAZON_SOC", "N_COMP", "FECHA", "IMPORTE")

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
    df = df.rename("Denominación o Razón Social").into("RAZON_SOC")
    df = df.rename("Importe Ret./Perc.").into("IMPORTE")
    df = df.rename("Número Comprobante").into("N_COMP")
    df = df.rename("Fecha Comprobante").into("FECHA")
    df = df.add("ORIGEN") { "AFIP" }
    df = df.convert("CUIT").with { it.toString() }

    df = df.convert("FECHA").with { it.toString() }

    return df
}

private fun setupTablaTango(ruta: Path): DataFrame<*> {
    var df = DataFrame.readExcel(ruta.toString())

    df = df.select {
        col("CUIT") and col("RAZON_SOC") and col("N_COMP") and col("FECH_COMP") and col("IMPORTE")
    }

    df = df.rename("FECH_COMP").into("FECHA")
    df = df.add("ORIGEN") { "TANGO" }
    df = df.convert("FECHA").with {
        val fecha = stringToLocalDate(it.toString())
        String.format("%02d/%02d/%02d", fecha.dayOfMonth, fecha.monthNumber, fecha.year)
    }
    df = df.convert { "CUIT"<String>() }.with { it.replace("-", "") }
    return df
}

@Throws(IllegalStateException::class)
fun setupTablasAfip(rutas: List<Path>): DataFrame<*> {
    val tablas = mutableListOf<DataFrame<*>>()

    for (ruta in rutas) {
        val rutaProcesada = setupTablaAfip(ruta)
        tablas.add(rutaProcesada)
    }
    return tablas.concat()
}

@Throws(IllegalStateException::class)
fun setupTablasTango(rutas: List<Path>): DataFrame<*> {
    val tablas = mutableListOf<DataFrame<*>>()

    for (ruta in rutas) {
        val rutaProcesada = setupTablaTango(ruta)
        tablas.add(rutaProcesada)
    }
    return tablas.concat()
}
