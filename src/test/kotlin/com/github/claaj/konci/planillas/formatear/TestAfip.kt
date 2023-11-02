package com.github.claaj.konci.planillas.formatear

import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.junit.jupiter.api.Test
import kotlin.io.path.Path
import kotlin.test.assertEquals

class TestAfip {
    @Test
    fun testSetupAfip() {
        val numerosFilasEsperado = 9
        val numerosFiltroEsperado = 2
        val archivosPrueba = listOf(Path("src/test/resources/Prueba-AFIP-1.xlsx"))
        val df = tablasAfip(archivosPrueba)
        assertEquals(numerosFilasEsperado, df.count(), "El número de filas no coincide.")
        assertEquals(
            numerosFiltroEsperado,
            df.filter { "CUIT"<String>() == "30901299992" }.count(),
            "El número de filas del filtrado no coincide."
        )
    }
}

