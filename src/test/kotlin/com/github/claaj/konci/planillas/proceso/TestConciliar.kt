package com.github.claaj.konci.planillas.proceso

import com.github.claaj.konci.planillas.formatear.tablasAfip
import com.github.claaj.konci.planillas.formatear.tablasTango
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.print
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class TestConciliar {
    @Test
    fun testConciliar() {
        val numerosFilasEsperado = 2
        val numerosFilasAfip = 1
        val numerosFilasTango = 1
        val df = conciliar(
            archivosExternos = listOf(Path("src/test/resources/Prueba-AFIP-1.xlsx")),
            archivosLocales = listOf(Path("src/test/resources/Prueba-Tango-1.xlsx")),
            formatearExternos = ::tablasAfip,
            formatearLocales = ::tablasTango,
        )
        assertEquals(numerosFilasEsperado, df.count(), "El número de filas no coincide.")
        assertEquals(
            numerosFilasAfip,
            df.filter { "ORIGEN"<String>() == "AFIP" }.count(),
            "El número de filas con origen de AFIP no coincide."
        )
        assertEquals(
            numerosFilasTango,
            df.filter { "ORIGEN"<String>() == "TANGO" }.count(),
            "El número de filas con origen de Tango no coincide."
        )
        df.print()
    }
}