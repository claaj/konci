package com.github.claaj.konci.data.process

import com.github.claaj.konci.enums.Impuestos
import com.github.claaj.konci.enums.Regimen
import kotlin.io.path.Path
import kotlin.test.Test

class TestRetenciones: TestConciliar {

    @Test
    fun testGananciasIva() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/composeResources/files/Retenciones/Ganancias-IVA/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/composeResources/files/Retenciones/Ganancias-IVA/Tango.xlsx")),
            // IVA y GANANCIAS tienen el mismo proceso.
            impuesto = Impuestos.IVA,
            regimen = Regimen.Retenciones,
            expectedRows = 1,
        )
    }

    @Test
    fun testIIBB() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/composeResources/files/Retenciones/IIBB/ConvMultilateral.txt")),
            pathsLocal = listOf(Path("src/commonTest/composeResources/files/Retenciones/IIBB/Tango.xlsx")),
            impuesto = Impuestos.IIBB,
            regimen = Regimen.Retenciones,
            expectedRows = 0,
        )
    }

    @Test
    fun testSuss() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/composeResources/files/Retenciones/SUSS/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/composeResources/files/Retenciones/SUSS/Tango.xlsx")),
            impuesto = Impuestos.SUSS,
            regimen = Regimen.Retenciones,
            expectedRows = 1,
        )
    }
}
