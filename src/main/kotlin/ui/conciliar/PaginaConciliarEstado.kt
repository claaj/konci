package ui.conciliar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class PaginaConciliarEstado(
    val titulo: String
) {
    var indexActual: MutableState<Int> = mutableStateOf(0)
    val impuestos: List<Impuestos> = Impuestos.entries
}