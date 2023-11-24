package com.github.claaj.konci.planillas.formatear

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestAfipSuss : TestFormatear {

    @Test
    fun testAfipSuss() {
        testFormatear(
            cuit1 = "30223334445",
            cuit2 = "30334445556",
            funcionFormatear = ::tablasSuss,
            archivos = listOf(Path("src/test/resources/Retenciones/SUSS/AFIP.xls")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 2
        )
    }

    @Test
    fun testTangoSuss() {
        testFormatear(
            cuit1 = "30223334445",
            cuit2 = "30334445556",
            funcionFormatear = ::tablasTangoRetenciones,
            archivos = listOf(Path("src/test/resources/Retenciones/SUSS/Tango.xlsx")),
            filasEsperadasCuit1 = 1,
            filasEsperadasCuit2 = 1
        )
    }
}
