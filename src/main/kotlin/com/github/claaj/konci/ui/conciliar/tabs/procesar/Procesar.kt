package com.github.claaj.konci.ui.conciliar.tabs.procesar

import com.github.claaj.konci.planillas.errores.ProcesoException
import com.github.claaj.konci.planillas.formatear.*
import com.github.claaj.konci.planillas.proceso.conciliar
import com.github.claaj.konci.planillas.proceso.fechaMayorTabla
import com.github.claaj.konci.planillas.proceso.localDateAStringAnioMes
import com.github.claaj.konci.ui.conciliar.Impuesto
import com.github.claaj.konci.ui.conciliar.Regimen
import com.github.claaj.konci.ui.conciliar.tabs.Dialogo
import com.github.claaj.konci.ui.conciliar.tabs.procesar.errores.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.io.writeExcel
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists

internal fun conciliarActuales(
    rutaCarpeta: String?,
    nombreCarpeta: String,
    nombreArchivo: String,
    regimen: Regimen,
    impuesto: Impuesto,
    archivosExternos: List<Path>,
    archivosLocales: List<Path>,
    origenExterno: String,
    origenLocal: String,
    dialogo: (String, Dialogo) -> Unit,
    abrirDialogoProcesando: () -> Unit,
    cerrarDialogoProcesando: () -> Unit,
) {
    var tipoDialogo = Dialogo.Exito
    var descripcionDialogo = "Se procesaron correctamente todas las planillas."

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val (formatearExternos, formatearLocales) = funcionesFormatear(regimen, impuesto)
            checkEstadosParaConciliar(
                archivosExternos,
                archivosLocales,
                rutaCarpeta,
                origenExterno,
                origenLocal
            )
            abrirDialogoProcesando()
            val dfFiltrado =
                conciliar(archivosExternos, archivosLocales, formatearExternos, formatearLocales, impuesto)
            guardadoProcesado(rutaCarpeta as String, nombreCarpeta, nombreArchivo, dfFiltrado)
        } catch (listaExcep: ListaException) {
            tipoDialogo = Dialogo.Aviso
            descripcionDialogo = listaExcep.message ?: ""
        } catch (procesoExcep: ProcesoException) {
            tipoDialogo = Dialogo.Error
            descripcionDialogo = procesoExcep.message ?: ""
        } catch (rutaExcep: RutaGuardadoException) {
            tipoDialogo = Dialogo.Aviso
            descripcionDialogo = rutaExcep.message ?: ""
        } finally {
            cerrarDialogoProcesando()
            dialogo(descripcionDialogo, tipoDialogo)
        }
    }
}

internal fun guardadoProcesado(rutaCarpeta: String, nombreCarpeta: String, nombreArchivo: String, df: DataFrame<*>) {
    val fecha = fechaMayorTabla(df)
    val fechaString = localDateAStringAnioMes(fecha)
    val rutaGuardado = Path(rutaCarpeta, nombreCarpeta, fechaString)
    Files.createDirectories(rutaGuardado)
    val rutaGuardadoArchivo = File(rutaGuardado.toString(), "$fechaString-$nombreArchivo.xlsx")
    df.writeExcel(rutaGuardadoArchivo)
}

internal fun checkEstadosParaConciliar(
    listaExterna: List<Path>, listaLocal: List<Path>, rutaGuardado: String?, origenExterno: String, origenLocal: String
) {
    if (rutaGuardado == null) throw RutaGuardadoSinSeleccionarException()

    if (!Path(rutaGuardado).exists()) throw RutaGuardadoNoExisteException(rutaGuardado)

    if (!File(rutaGuardado).canWrite()) throw RutaGuardadoNoEscrituraException(rutaGuardado)

    if (listaExterna.isEmpty()) throw ListaVaciaException(origenExterno)

    if (listaLocal.isEmpty()) throw ListaVaciaException(origenLocal)

    for (rutaExterna in listaExterna) {
        if (!rutaExterna.exists()) throw ListaArchivoNoExiste(origenExterno, rutaExterna.toString())
    }

    for (rutalocal in listaLocal) {
        if (!rutalocal.exists()) throw ListaArchivoNoExiste(origenLocal, rutalocal.toString())
    }
}

internal fun funcionesFormatear(
    regimen: Regimen,
    impuesto: Impuesto
): Pair<(List<Path>) -> DataFrame<*>, (List<Path>) -> DataFrame<*>> {
    return when (regimen) {
        Regimen.Percepciones -> {
            when (impuesto) {
                Impuesto.GANANCIAS -> Pair(::tablasAfip, ::tablasTangoPercepciones)
                Impuesto.IIBB -> Pair(::tablasConvenioMultiPercep, ::tablasTangoIIBBPercep)
                Impuesto.IVA -> Pair(::tablasAfip, ::tablasTangoPercepciones)
                Impuesto.SUSS -> Pair(::tablasSuss, ::tablasTangoPercepciones)
            }
        }

        Regimen.Retenciones -> {
            when (impuesto) {
                Impuesto.GANANCIAS -> Pair(::tablasAfip, ::tablasTangoRetenciones)
                Impuesto.IIBB -> Pair(::tablasConvenioMultiReten, ::tablasTangoIIBBRet)
                Impuesto.IVA -> Pair(::tablasAfip, ::tablasTangoRetenciones)
                Impuesto.SUSS -> Pair(::tablasSuss, ::tablasTangoRetenciones)
            }
        }

        Regimen.Ambos -> throw UnsupportedOperationException()
    }
}
