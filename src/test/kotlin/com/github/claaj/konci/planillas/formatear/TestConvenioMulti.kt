package com.github.claaj.konci.planillas.formatear

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestConvenioMulti : TestFormatear {

    @Test
    fun convenioMultiLateralPercepciones() {
        testFormatear(
            cuit1 = "30123334441",
            cuit2 = "30777777777",
            funcionFormatear = ::tablasConvenioMultiPercep,
            archivos = listOf(Path("src/test/resources/Percepciones/IIBB/ConvMultilateral.txt")),
            filasEsperadasCuit1 = 2,
            filasEsperadasCuit2 = 1
        )
    }

    @Test
    fun convenioMultiLateralRetenciones() {
        testFormatear(
            cuit1 = "30112223334",
            cuit2 = "30332221115",
            funcionFormatear = ::tablasConvenioMultiReten,
            archivos = listOf(Path("src/test/resources/Retenciones/IIBB/ConvMultilateral.txt")),
            filasEsperadasCuit1 = 1,
            filasEsperadasCuit2 = 1,
        )
    }
}
