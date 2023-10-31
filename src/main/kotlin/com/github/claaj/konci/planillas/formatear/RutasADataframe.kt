package com.github.claaj.konci.planillas.formatear

import com.github.claaj.konci.planillas.errores.FormatoInvalidoException
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.concat
import java.nio.file.Path

internal fun rutasADataframe(rutas: List<Path>, procesarPlanilla: (Path) -> DataFrame<*>): DataFrame<*> {
    val tablas = mutableListOf<DataFrame<*>>()

    for (ruta in rutas) {
        try {
            val rutaProcesada = procesarPlanilla(ruta)
            tablas.add(rutaProcesada)
        } catch (_: IllegalStateException) {
            throw FormatoInvalidoException(ruta.toString())
        }
    }
    return tablas.concat()
}
