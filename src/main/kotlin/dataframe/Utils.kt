package dataframe

import kotlinx.datetime.LocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.getColumn

fun stringToLocalDate(cadena: String?): LocalDate {
    var fecha = LocalDate(2000, 1, 1)
    if (cadena != null) {
        val split = cadena.dropLast(6).split("-")
        if (split.size == 3) fecha = LocalDate(split[0].toInt(), split[1].toInt(), split[2].toInt())
    }
    return fecha
}

fun fechaMayorTabla(df: DataFrame<*>): LocalDate {
    val fechas = df.getColumn { "FECHA"<String>() }.toList()
    var fechaMayor = LocalDate(2000, 1, 1)
    for (fecha in fechas) {
        val fechaCast = fechaMayorConversion(fecha)
        if (fechaMayor < fechaCast) {
            fechaMayor = fechaCast
        }
    }
    return fechaMayor
}

private fun fechaMayorConversion(cadena: String): LocalDate {
    val split = cadena.split("/")
    return LocalDate(split[2].toInt(), split[1].toInt(), split[0].toInt())
}

fun cuitsUnicos(dfExterno: DataFrame<*>, dfLocal: DataFrame<*>): List<String> {
    val listaExternos = listaCuits(dfExterno)
    val listaLocales = listaCuits(dfLocal)
    val listaUnicos = ArrayList<String>()

    for (cuitExterno in listaExternos) {
        if (!listaUnicos.contains(cuitExterno)) {
            listaUnicos.add(cuitExterno)
        }
    }

    for (cuitLocal in listaLocales) {
        if (!listaUnicos.contains(cuitLocal)) {
            listaUnicos.add(cuitLocal)
        }
    }
    return listaUnicos
}

fun listaCuits(df: DataFrame<*>): List<String> {
    return df.getColumn { "CUIT"<String>() }.toList()
}

fun importesPorCuit(df: DataFrame<*>, cuit: String): List<Double> {
    return df.filter { "CUIT"<String>() == cuit }.getColumn { "IMPORTE"<Double>() }.toList()
}

fun importesPorCuitUnicos(df: DataFrame<*>, cuit: String): ArrayList<Double> {
    val importes = importesPorCuit(df, cuit)
    val importesUnicos = ArrayList<Double>()
    for (importe in importes) {
        if (!importesUnicos.contains(importe)) {
            importesUnicos.add(importe)
        }
    }
    return importesUnicos
}

fun importesPorCuitUnicosTotal(dfExterno: DataFrame<*>, dfLocal: DataFrame<*>, cuit: String): List<Double> {
    val importesExternos = importesPorCuitUnicos(dfExterno, cuit)
    val importesLocales = importesPorCuitUnicos(dfLocal, cuit)

    for (importe in importesLocales) {
        if (!importesExternos.contains(importe)) {
            importesExternos.add(importe)
        }
    }
    return importesExternos
}

fun mismoImporteTotal(dfExterno: DataFrame<*>, dfLocal: DataFrame<*>, cuit: String): Boolean {
    val listaImporteExterno = importesPorCuit(dfExterno, cuit)
    val listaImporteLocal = importesPorCuit(dfLocal, cuit)

    val totalExterno = sumarImportes(listaImporteExterno)
    val totalLocal = sumarImportes(listaImporteLocal)

    return totalExterno == totalLocal
}

fun sumarImportes(listaImportes: List<Double>): Double {
    var total = 0.0
    for (importe in listaImportes) {
        total += importe
    }
    return total
}

fun filtroCuitImporte(df: DataFrame<*>, cuit: String, importe: Double): DataFrame<*> {
    return df.filter { "CUIT"<String>() == cuit && "IMPORTE"<Double>() == importe }
}