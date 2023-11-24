package com.github.claaj.konci.planillas.formatear

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestTango : TestFormatear {
    @Test
    fun testTangoPercepciones() {
        testFormatear(
            cuit1 = "30445556667",
            cuit2 = "30112226664",
            funcionFormatear = ::tablasTangoPercepciones,
            archivos = listOf(Path("src/test/resources/Percepciones/IVA/Tango.xlsx")),
            filasEsperadasCuit1 = 1,
            filasEsperadasCuit2 = 1
        )
    }

    @Test
    fun testTangoRetenciones() {
        testFormatear(
            cuit1 = "30887776665",
            cuit2 = "30112223334",
            funcionFormatear = ::tablasTangoRetenciones,
            archivos = listOf(Path("src/test/resources/Retenciones/Ganancias-IVA/Tango.xlsx")),
            filasEsperadasCuit1 = 1,
            filasEsperadasCuit2 = 1
        )
    }
}
