package com.github.claaj.konci.data.format

import org.junit.Test
import kotlin.io.path.Path

class TestTango: TestFormat {
    @Test
    fun testTangoPercepciones() {
        testFormat(
                cuit1 = "30445556667",
                cuit2 = "30112226664",
                fnFormat = ::dfTangoPercepciones,
                paths = listOf(Path("src/commonTest/composeResources/files/Percepciones/IVA/Tango.xlsx")),
                expectedRows1 = 1,
                expectedRows2 = 1
        )
    }

    @Test
    fun testTangoRetenciones() {
        testFormat(
                cuit1 = "30887776665",
                cuit2 = "30112223334",
                fnFormat = ::dfTangoRetenciones,
                paths = listOf(Path("src/commonTest/composeResources/files/Retenciones/Ganancias-IVA/Tango.xlsx")),
                expectedRows1 = 1,
                expectedRows2 = 1
        )
    }
}
