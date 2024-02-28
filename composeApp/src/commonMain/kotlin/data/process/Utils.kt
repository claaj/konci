package data.process

import kotlinx.datetime.LocalDate
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.getColumn
import org.jetbrains.kotlinx.dataframe.api.last
import java.math.BigDecimal

fun stringToLocalDate(string: String): LocalDate {
    val split = string.split("/")
    return LocalDate(split[2].toInt(), split[1].toInt(), split[0].toInt())
}

fun cuitAfiptoString(cuit: Any?): String {
    return when (cuit) {
        is Double -> BigDecimal(cuit).toPlainString()
        else -> cuit.toString()
    }
}

fun getMostRecentDate(df: DataFrame<*>): LocalDate {
    val date = df.getColumn { "FECHA"<LocalDate>() }
    var dateNewest = date[0]
    var dateFilter = date.filter { it > dateNewest }
    while (dateFilter.count() > 0) {
        dateNewest = dateFilter.last()
        dateFilter = dateFilter.filter { it > dateNewest }
    }
    return dateNewest
}

fun uniqueCuits(dfExternal: DataFrame<*>, dfLocal: DataFrame<*>): List<String> {
    val cuitsExternal = cuits(dfExternal)
    val cuitsLocal = cuits(dfLocal)
    val cuitsUnique = mutableListOf<String>()

    for (cuitExternal in cuitsExternal) {
        if (!cuitsUnique.contains(cuitExternal)) {
            cuitsUnique.add(cuitExternal)
        }
    }

    for (cuitLocal in cuitsLocal) {
        if (!cuitsUnique.contains(cuitLocal)) {
            cuitsUnique.add(cuitLocal)
        }
    }
    return cuitsUnique
}

fun cuits(df: DataFrame<*>): List<String> {
    return df.getColumn { "CUIT"<String>() }.toList()
}

private fun importesByCuit(df: DataFrame<*>, cuit: String): List<Double> {
    return df.filter { "CUIT"<String>() == cuit }.getColumn { "IMPORTE"<Double>() }.toList()
}

fun uniqueImportesByCuit(df: DataFrame<*>, cuit: String): List<Double> {
    val importes = importesByCuit(df, cuit)
    val importesUniques = mutableListOf<Double>()
    for (importe in importes) {
        if (!importesUniques.contains(importe)) {
            importesUniques.add(importe)
        }
    }
    return importesUniques
}

fun uniqueImportesByCuit(dfExternal: DataFrame<*>, dfLocal: DataFrame<*>, cuit: String): List<Double> {
    val importesExternal = uniqueImportesByCuit(dfExternal, cuit).toMutableList()
    val importesLocal = uniqueImportesByCuit(dfLocal, cuit)

    for (importe in importesLocal) {
        if (!importesExternal.contains(importe)) {
            importesExternal.add(importe)
        }
    }
    return importesExternal
}

fun checkImportesSum(dfExternal: DataFrame<*>, dfLocal: DataFrame<*>, cuit: String): Boolean {
    val importesExternal = importesByCuit(dfExternal, cuit)
    val importesLocal = importesByCuit(dfLocal, cuit)

    val totalExternal = importesSum(importesExternal)
    val totalLocal = importesSum(importesLocal)

    return totalExternal == totalLocal
}

fun importesSum(importes: List<Double>): Double {
    var total = 0.0
    for (importe in importes) {
        total += importe
    }
    return total
}

fun filterByCuitImporte(df: DataFrame<*>, cuit: String, importe: Double): DataFrame<*> {
    return df.filter { "CUIT"<String>() == cuit && "IMPORTE"<Double>() == importe }
}

fun localDateToStringYearMonth(date: LocalDate): String {
    return "${date.year}-${String.format("%02d", date.monthNumber)}"
}

fun intToProvinciaString(numero: Int): String {
    return when (numero) {
        901 -> "CABA"
        902 -> "Buenos Aires"
        903 -> "Catamarca"
        904 -> "Cordoba"
        905 -> "Corrientes"
        906 -> "Chaco"
        907 -> "Chubut"
        908 -> "Entre Rios"
        909 -> "Formosa"
        910 -> "Jujuy"
        911 -> "La Pampa"
        912 -> "La Rioja"
        913 -> "Mendoza"
        914 -> "Misiones"
        915 -> "Neuquen"
        916 -> "Rio Negro"
        917 -> "Salta"
        918 -> "San Juan"
        919 -> "San Luis"
        920 -> "Santa Cruz"
        921 -> "Santa Fe"
        922 -> "Santiago del Estero"
        923 -> "Tierra del Fuego"
        924 -> "Tucuman"
        else -> "Codigo Invalido"
    }
}
