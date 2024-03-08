package com.github.claaj.konci.data.process

import com.github.claaj.konci.enums.Impuestos
import com.github.claaj.konci.enums.Regimen
import org.junit.Test
import kotlin.io.path.Path

class TestPercepciones: TestConciliar {

    @Test
    fun testIIBB() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/composeResources/files/Percepciones/IIBB/ConvMultilateral.txt")),
            pathsLocal = listOf(Path("src/commonTest/composeResources/files/Percepciones/IIBB/Tango.xlsx")),
            impuesto = Impuestos.IIBB,
            regimen = Regimen.Percepciones,
            expectedRows = 0,
        )
    }

    @Test
    fun testIva() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/composeResources/files/Percepciones/IVA/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/composeResources/files/Percepciones/IVA/Tango.xlsx")),
            impuesto = Impuestos.IVA,
            regimen = Regimen.Percepciones,
            expectedRows = 1,
        )
    }
}
