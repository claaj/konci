package com.github.claaj.konci.data.format

import org.junit.Test
import kotlin.io.path.Path

class TestConvenioMulti: TestFormat {

    @Test
    fun convenioMultiLateralPercepciones() {
        testFormat(
            cuit1 = "30123334441",
            cuit2 = "30777777777",
            fnFormat = ::dfConvenioMultiPercepciones,
            paths = listOf(Path("src/commonTest/composeResources/files/Percepciones/IIBB/ConvMultilateral.txt")),
            expectedRows1 = 2,
            expectedRows2 = 1
        )
    }

    @Test
    fun convenioMultiLateralRetenciones() {
        testFormat(
            cuit1 = "30112223334",
            cuit2 = "30332221115",
            fnFormat = ::dfConvenioMultiRetenciones,
            paths = listOf(Path("src/commonTest/composeResources/files/Retenciones/IIBB/ConvMultilateral.txt")),
            expectedRows1 = 1,
            expectedRows2 = 1,
        )
    }
}
