package com.github.claaj.konci.planillas.proceso

import com.github.claaj.konci.planillas.formatear.tablasAfip
import com.github.claaj.konci.planillas.formatear.tablasConvenioMultiPercep
import com.github.claaj.konci.planillas.formatear.tablasTangoIIBBPercep
import com.github.claaj.konci.planillas.formatear.tablasTangoPercepciones
import com.github.claaj.konci.ui.conciliar.Impuesto
import org.junit.jupiter.api.Test
import kotlin.io.path.Path

class TestPercepciones : TestConciliar {

    @Test
    fun testIIBB() {
        testConciliar(
            archivosExternos = listOf(Path("src/test/resources/Percepciones/IIBB/ConvMultilateral.txt")),
            archivosLocales = listOf(Path("src/test/resources/Percepciones/IIBB/Tango.xlsx")),
            formatearExternos = ::tablasConvenioMultiPercep,
            formatearLocales = ::tablasTangoIIBBPercep,
            impuesto = Impuesto.IIBB,
            numerosFilasEsperado = 0,
        )
    }

    @Test
    fun testIva() {
        testConciliar(
            archivosExternos = listOf(Path("src/test/resources/Percepciones/IVA/AFIP.xls")),
            archivosLocales = listOf(Path("src/test/resources/Percepciones/IVA/Tango.xlsx")),
            formatearExternos = ::tablasAfip,
            formatearLocales = ::tablasTangoPercepciones,
            impuesto = Impuesto.IVA,
            numerosFilasEsperado = 1,
        )
    }
}