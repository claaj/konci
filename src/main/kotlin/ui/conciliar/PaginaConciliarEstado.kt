package ui.conciliar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import ui.conciliar.tabs.TabsConciliarEstado

@Stable
data class PaginaConciliarEstado(
    val titulo: String
) {
    var indexActual: MutableState<Int> = mutableStateOf(0)
    val procesos: MutableList<TabsConciliarEstado> = mutableListOf()

    init {
        for (impuesto in Impuestos.entries) {
            val tabsEstado = TabsConciliarEstado(impuesto)
            procesos.add(tabsEstado.copy())
        }
    }
}