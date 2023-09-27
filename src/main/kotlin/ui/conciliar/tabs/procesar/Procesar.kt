package ui.conciliar.tabs.procesar

import dataframe.conciliar
import dataframe.fechaMayorTabla
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.writeExcel
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists

internal fun conciliarActuales(
    rutaCarpeta: String,
    nombreCarpeta: String,
    nombreArchivo: String,
    archivosExternos: List<Path>,
    archivosLocales: List<Path>,
    setupExternos: (List<Path>) -> DataFrame<*>,
    setupLocales: (List<Path>) -> DataFrame<*>,
    dialogoFaltanDatos: () -> Unit,
    dialogoArchivoNoEncotrado: () -> Unit,
    dialogoFormatoInvalido: () -> Unit,
    dialogoTodoOk: () -> Unit,
) {
    try {
        checkEstadosParaConciliar(archivosExternos, archivosLocales, rutaCarpeta)
        val dfFiltrado = conciliar(archivosExternos, archivosLocales, setupExternos, setupLocales)
        guardadoProcesado(rutaCarpeta, nombreCarpeta, nombreArchivo, dfFiltrado)
        dialogoTodoOk()
    } catch (_: FileNotFoundException) {
        dialogoArchivoNoEncotrado()
    } catch (_: FaltanDatosConciliarException) {
        dialogoFaltanDatos()
    } catch (_: IllegalStateException) {
        dialogoFormatoInvalido()
    }
}

private fun guardadoProcesado(rutaCarpeta: String, nombreCarpeta: String, nombreArchivo: String, df: DataFrame<*>) {
    val fecha = fechaMayorTabla(df)
    val rutaGuardado = Path(rutaCarpeta, nombreCarpeta, "${fecha.year}-${String.format("%02d", fecha.monthNumber)}")
    Files.createDirectories(rutaGuardado)
    val rutaGuardadoArchivo = File(rutaGuardado.toString(), "$nombreArchivo.xlsx")
    df.writeExcel(rutaGuardadoArchivo)
}

private fun checkEstadosParaConciliar(
    listaExterna: List<Path>,
    listaLocal: List<Path>,
    rutaGuardado: String
) {
    if (!(rutaGuardado != "" && listaExterna.isNotEmpty() && listaLocal.isNotEmpty())) throw FaltanDatosConciliarException()

    for (rutaExterna in listaExterna) {
        if (!rutaExterna.exists()) throw FileNotFoundException()
        println(rutaExterna.exists())
    }

    for (rutalocal in listaLocal) {
        if (!rutalocal.exists()) throw FileNotFoundException()
    }

    if (!Path(rutaGuardado).exists()) throw FileNotFoundException()
}

internal class FaltanDatosConciliarException : Exception()