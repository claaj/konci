package data.format

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.print
import java.nio.file.Path
import kotlin.test.assertEquals

interface TestFormat {

    fun testFormat(
        cuit1: String,
        cuit2: String,
        fnFormat: (List<Path>) -> DataFrame<*>,
        paths: List<Path>,
        expectedRows1: Int,
        expectedRows2: Int,
    ) {
        val df = fnFormat(paths)

        assertEquals(
            expectedRows1,
            df.filter { "CUIT"<String>() == cuit1 }.count(),
            "El número de filas del filtrado de $cuit1 no coincide."
        )

        assertEquals(
            expectedRows2,
            df.filter { "CUIT"<String>() == cuit2 }.count(),
            "El número de filas del filtrado de $cuit2 no coincide."
        )

        df.print()
    }
}
