package data.format

import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestAfipSuss : TestFormat {

    @Test
    fun testAfipSuss() {
        testFormat(
            cuit1 = "30223334445",
            cuit2 = "30334445556",
            fnFormat = ::dfSuss,
            paths = listOf(Path("src/commonTest/resources/Retenciones/SUSS/AFIP.xls")),
            expectedRows1 = 2,
            expectedRows2 = 2
        )
    }

    @Test
    fun testTangoSuss() {
        testFormat(
            cuit1 = "30223334445",
            cuit2 = "30334445556",
            fnFormat = ::dfTangoRetenciones,
            paths = listOf(Path("src/commonTest/resources/Retenciones/SUSS/Tango.xlsx")),
            expectedRows1 = 1,
            expectedRows2 = 1
        )
    }
}
