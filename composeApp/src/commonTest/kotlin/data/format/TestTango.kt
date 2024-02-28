package data.format

import kotlin.io.path.Path
import org.junit.jupiter.api.Test

class TestTango : TestFormat {
    @Test
    fun testTangoPercepciones() {
        testFormat(
                cuit1 = "30445556667",
                cuit2 = "30112226664",
                fnFormat = ::dfTangoPercepciones,
                paths = listOf(Path("src/commonTest/resources/Percepciones/IVA/Tango.xlsx")),
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
                paths = listOf(Path("src/commonTest/resources/Retenciones/Ganancias-IVA/Tango.xlsx")),
                expectedRows1 = 1,
                expectedRows2 = 1
        )
    }
}
