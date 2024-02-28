package data.process

import enums.Impuestos
import enums.Regimen
import kotlin.io.path.Path
import kotlin.test.Test

class TestRetenciones : TestConciliar {

    @Test
    fun testGananciasIva() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/resources/Retenciones/Ganancias-IVA/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/resources/Retenciones/Ganancias-IVA/Tango.xlsx")),
            // IVA y GANANCIAS tienen el mismo proceso.
            impuesto = Impuestos.IVA,
            regimen = Regimen.Retenciones,
            expectedRows = 1,
        )
    }

    @Test
    fun testIIBB() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/resources/Retenciones/IIBB/ConvMultilateral.txt")),
            pathsLocal = listOf(Path("src/commonTest/resources/Retenciones/IIBB/Tango.xlsx")),
            impuesto = Impuestos.IIBB,
            regimen = Regimen.Retenciones,
            expectedRows = 0,
        )
    }

    @Test
    fun testSuss() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/resources/Retenciones/SUSS/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/resources/Retenciones/SUSS/Tango.xlsx")),
            impuesto = Impuestos.SUSS,
            regimen = Regimen.Retenciones,
            expectedRows = 1,
        )
    }
}
