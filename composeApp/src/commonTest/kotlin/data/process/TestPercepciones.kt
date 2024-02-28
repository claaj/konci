package data.process

import enums.Impuestos
import enums.Regimen
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestPercepciones : TestConciliar {

    @Test
    fun testIIBB() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/resources/Percepciones/IIBB/ConvMultilateral.txt")),
            pathsLocal = listOf(Path("src/commonTest/resources/Percepciones/IIBB/Tango.xlsx")),
            impuesto = Impuestos.IIBB,
            regimen = Regimen.Percepciones,
            expectedRows = 0,
        )
    }

    @Test
    fun testIva() {
        testConciliar(
            pathsExternal = listOf(Path("src/commonTest/resources/Percepciones/IVA/AFIP.xls")),
            pathsLocal = listOf(Path("src/commonTest/resources/Percepciones/IVA/Tango.xlsx")),
            impuesto = Impuestos.IVA,
            regimen = Regimen.Percepciones,
            expectedRows = 1,
        )
    }
}
