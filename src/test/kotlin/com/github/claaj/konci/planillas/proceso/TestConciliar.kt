package com.github.claaj.konci.planillas.proceso

import com.github.claaj.konci.ui.conciliar.Impuesto
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.print
import java.nio.file.Path
import kotlin.test.assertEquals

interface TestConciliar {

    fun testConciliar(
        archivosExternos: List<Path>,
        archivosLocales: List<Path>,
        formatearExternos: (List<Path>) -> DataFrame<*>,
        formatearLocales: (List<Path>) -> DataFrame<*>,
        impuesto: Impuesto,
        numerosFilasEsperado: Int,
    ) {
        val df = conciliar(
            archivosExternos = archivosExternos,
            archivosLocales = archivosLocales,
            formatearExternos = formatearExternos,
            formatearLocales = formatearLocales,
            impuesto = impuesto
        )

        df.print()

        assertEquals(
            numerosFilasEsperado,
            df.count(),
        )
    }
}
