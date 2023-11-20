package com.github.claaj.konci.ui.conciliar.tabs

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.claaj.konci.ui.conciliar.Impuesto
import com.github.claaj.konci.ui.conciliar.Regimen
import java.nio.file.Path

@Stable
data class TabsConciliarEstado(val regimen: Regimen, val impuesto: Impuesto) {
    val listaExterna: SnapshotStateList<Path> = mutableStateListOf()
    val listaLocal: SnapshotStateList<Path> = mutableStateListOf()
    var indexActual: Int by mutableStateOf(0)
    val origenLocal: String
    val origenExterno: String
    val extensionesExternos: List<String>
    val extensionesLocales: List<String>

    init {
        when (impuesto) {
            Impuesto.GANANCIAS -> {
                extensionesExternos = listOf("xls", "xlsx")
                extensionesLocales = listOf("xls", "xlsx")
                origenExterno = "AFIP"
                origenLocal = "Tango"
            }

            Impuesto.IIBB -> {
                extensionesExternos = listOf("txt")
                extensionesLocales = listOf("xls", "xlsx")
                origenExterno = "Conv. Multilateral"
                origenLocal = "Tango"
            }

            Impuesto.IVA -> {
                extensionesExternos = listOf("xls", "xlsx")
                extensionesLocales = listOf("xls", "xlsx")
                origenExterno = "AFIP"
                origenLocal = "Tango"
            }

            Impuesto.SUSS -> {
                extensionesExternos = listOf("xls", "xlsx")
                extensionesLocales = listOf("xls", "xlsx")
                origenExterno = "AFIP"
                origenLocal = "Tango"
            }
        }
    }
}
