package com.github.claaj.konci.planillas.proceso

import com.github.claaj.konci.planillas.formatear.*
import com.github.claaj.konci.ui.conciliar.Impuesto
import kotlin.io.path.Path
import kotlin.test.Test

class TestRetenciones : TestConciliar {

    @Test
    fun testGananciasIva() {
        testConciliar(
            archivosExternos = listOf(Path("src/test/resources/Retenciones/Ganancias-IVA/AFIP.xls")),
            archivosLocales = listOf(Path("src/test/resources/Retenciones/Ganancias-IVA/Tango.xlsx")),
            formatearExternos = ::tablasAfip,
            formatearLocales = ::tablasTangoRetenciones,
            // IVA y GANANCIAS tienen el mismo proceso.
            impuesto = Impuesto.IVA,
            numerosFilasEsperado = 1,
        )
    }

    @Test
    fun testIIBB() {
        testConciliar(
            archivosExternos = listOf(Path("src/test/resources/Retenciones/IIBB/ConvMultilateral.txt")),
            archivosLocales = listOf(Path("src/test/resources/Retenciones/IIBB/Tango.xlsx")),
            formatearExternos = ::tablasConvenioMultiReten,
            formatearLocales = ::tablasTangoIIBBRet,
            impuesto = Impuesto.IIBB,
            numerosFilasEsperado = 0,
        )
    }

    @Test
    fun testSuss() {
        testConciliar(
            archivosExternos = listOf(Path("src/test/resources/Retenciones/SUSS/AFIP.xls")),
            archivosLocales = listOf(Path("src/test/resources/Retenciones/SUSS/Tango.xlsx")),
            formatearExternos = ::tablasSuss,
            formatearLocales = ::tablasTangoRetenciones,
            impuesto = Impuesto.SUSS,
            numerosFilasEsperado = 1,
        )
    }
}