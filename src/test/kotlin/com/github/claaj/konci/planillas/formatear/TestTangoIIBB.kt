package com.github.claaj.konci.planillas.formatear

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestTangoIIBB : TestFormatear {

    @Test
    fun testIIBBPercepciones() {
        testFormatear(
            cuit1 = "30123334441",
            cuit2 = "30777777777",
            funcionFormatear = ::tablasTangoIIBBPercep,
            archivos = listOf(Path("src/test/resources/Percepciones/IIBB/Tango.xlsx")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 3
        )
    }

    @Test
    fun testIIBBRetenciones() {
        testFormatear(
            cuit1 = "30112223334",
            cuit2 = "30332221115",
            funcionFormatear = ::tablasTangoIIBBRet,
            archivos = listOf(Path("src/test/resources/Retenciones/IIBB/Tango.xlsx")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 3
        )
    }
}
