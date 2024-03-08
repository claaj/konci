package com.github.claaj.konci.enums

enum class Impuestos(val regimen: Regimen, val route: String) {
    Ganancias(Regimen.Retenciones, "/ganancias"),
    IIBB(Regimen.Ambos, "/iibb"),
    IVA(Regimen.Ambos, "/iva"),
    SUSS(Regimen.Retenciones, "/suss");

    fun getSources(): List<String> {
        return when(this) {
            IIBB -> listOf("Conv. Multilateral", "Tango")
            else -> listOf("AFIP", "Tango")
        }
    }

    fun getOuterFileExtensions(): List<String> {
        return when(this) {
            IIBB -> listOf("txt")
            else -> listOf("xls", "xlsx")
        }
    }

    fun getLocalFileExtensions(): List<String> {
        return listOf("xls", "xlsx")
    }

    companion object {
        fun filterByRegimen(regimen: Regimen): List<Impuestos> {
            val list = mutableListOf<Impuestos>()
            for(impuesto in Impuestos.entries) {
                if (impuesto.regimen == regimen || impuesto.regimen == Regimen.Ambos) {
                    list.add(impuesto)
                }
            }
            return list.toList()
        }
    }
}

