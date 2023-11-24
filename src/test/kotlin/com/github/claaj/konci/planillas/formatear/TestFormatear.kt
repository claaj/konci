package com.github.claaj.konci.planillas.formatear

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.print
import java.nio.file.Path
import kotlin.test.assertEquals

interface TestFormatear {

    fun testFormatear(
        cuit1: String,
        cuit2: String,
        funcionFormatear: (List<Path>) -> DataFrame<*>,
        archivos: List<Path>,
        filasEsperadasCuit1: Int,
        filasEsperadasCuit2: Int,
    ) {
        val df = funcionFormatear(archivos)

        assertEquals(
            filasEsperadasCuit1,
            df.filter { "CUIT"<String>() == cuit1 }.count(),
            "El número de filas del filtrado de $cuit1 no coincide."
        )

        assertEquals(
            filasEsperadasCuit2,
            df.filter { "CUIT"<String>() == cuit2 }.count(),
            "El número de filas del filtrado de $cuit2 no coincide."
        )

        df.print()
    }
}
