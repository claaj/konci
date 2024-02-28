package data.format

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestTangoIIBB : TestFormat {

    @Test
    fun testIIBBPercepciones() {
        testFormat(
            cuit1 = "30123334441",
            cuit2 = "30777777777",
            fnFormat = ::dfTangoIIBBPercepciones,
            paths = listOf(Path("src/commonTest/resources/Percepciones/IIBB/Tango.xlsx")),
            expectedRows1 = 2,
            expectedRows2 = 3
       )
    }

    @Test
    fun testIIBBRetenciones() {
        testFormat(
            cuit1 = "30112223334",
            cuit2 = "30332221115",
            fnFormat = ::dfTangoIIBBRetenciones,
            paths = listOf(Path("src/commonTest/resources/Retenciones/IIBB/Tango.xlsx")),
            expectedRows1 = 2,
            expectedRows2 = 3
        )
    }
}
