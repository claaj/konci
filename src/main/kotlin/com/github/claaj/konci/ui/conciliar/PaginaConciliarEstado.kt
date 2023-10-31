package com.github.claaj.konci.ui.conciliar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.claaj.konci.ui.conciliar.tabs.TabsConciliarEstado

@Stable
data class PaginaConciliarEstado(
    val titulo: String
) {
    var indexActual by mutableStateOf(0)
    val procesos: MutableList<TabsConciliarEstado> = mutableListOf()

    init {
        for (impuesto in Impuestos.entries) {
            val tabsEstado = TabsConciliarEstado(impuesto)
            procesos.add(tabsEstado.copy())
        }
    }
}