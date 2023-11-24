package com.github.claaj.konci.planillas.formatear

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestAfip : TestFormatear {

    @Test
    fun testAfipPercepciones() {
        testFormatear(
            cuit1 = "30445556667",
            cuit2 = "30112226664",
            funcionFormatear = ::tablasAfip,
            archivos = listOf(Path("src/test/resources/Percepciones/IVA/AFIP.xls")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 2
        )
    }

    @Test
    fun testAfipRetenciones() {
        testFormatear(
            cuit1 = "30887776665",
            cuit2 = "30112223334",
            funcionFormatear = ::tablasAfip,
            archivos = listOf(Path("src/test/resources/Retenciones/Ganancias-IVA/AFIP.xls")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 2
        )
    }
}


