package com.github.claaj.konci.data.format

import org.junit.Test
import kotlin.io.path.Path

class TestAfip: TestFormat {

    @Test
    fun testAfipPercepciones() {
        testFormat(
            cuit1 = "30445556667",
            cuit2 = "30112226664",
            fnFormat = ::dfAfip,
            paths = listOf(Path("src/commonTest/composeResources/files/Percepciones/IVA/AFIP.xls")),
            expectedRows1 = 2,
            expectedRows2 = 2
        )
    }

    @Test
    fun testAfipRetenciones() {
        testFormat(
            cuit1 = "30887776665",
            cuit2 = "30112223334",
            fnFormat = ::dfAfip,
            paths = listOf(Path("src/commonTest/composeResources/files/Retenciones/Ganancias-IVA/AFIP.xls")),
            expectedRows1 = 2,
            expectedRows2 = 2
        )
    }
}
