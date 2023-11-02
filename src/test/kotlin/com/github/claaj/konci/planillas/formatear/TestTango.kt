package com.github.claaj.konci.planillas.formatear

import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.print
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class TestTango {
    @Test
    fun testSetupTango() {
        val numerosFilasEsperado = 4
        val numerosFiltroEsperado = 1
        val archivosPrueba = listOf(Path("src/test/resources/Prueba-Tango-1.xlsx"))
        val df = tablasTango(archivosPrueba)
        df.print()
        assertEquals(numerosFilasEsperado, df.count(), "El número de filas no coincide.")
        assertEquals(
            numerosFiltroEsperado,
            df.filter { "CUIT"<String>() == "30901299992" }.count(),
            "El número de filas del filtrado no coincide."
        )
    }
}