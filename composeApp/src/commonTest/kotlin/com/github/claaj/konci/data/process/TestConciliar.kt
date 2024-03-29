package com.github.claaj.konci.data.process

import com.github.claaj.konci.enums.Impuestos
import com.github.claaj.konci.enums.Regimen
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.print
import java.nio.file.Path
import kotlin.test.assertEquals

interface TestConciliar {

    fun testConciliar(
        pathsExternal: List<Path>,
        pathsLocal: List<Path>,
        regimen: Regimen,
        impuesto: Impuestos,
        expectedRows: Int
    ) {
        val df = conciliarFiles(
            pathsExternal = pathsExternal,
            pathsLocal = pathsLocal,
            regimen = regimen,
            impuesto = impuesto
        )

        df.print()

        assertEquals(
            expectedRows,
            df.count(),
        )
    }
}
