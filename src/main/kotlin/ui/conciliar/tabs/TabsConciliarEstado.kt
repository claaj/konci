package ui.conciliar.tabs

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import ui.conciliar.tabs.lista.TabEstadoLista
import ui.conciliar.tabs.procesar.TabEstadoProcesar
import java.nio.file.Path


data class TabsConciliarEstado(
    val primerNombre: String,
    val segundoNombre: String,
    val tercerNombre: String,
) {
    var listaExterno = mutableStateListOf<Path>()
    var listaLocal = mutableStateListOf<Path>()
    val items: List<TabEstado> = listOf(
        TabEstadoLista(primerNombre, TabTipo.PLANILLA, listaExterno),
        TabEstadoLista(segundoNombre, TabTipo.PLANILLA, listaLocal),
        TabEstadoProcesar(tercerNombre, TabTipo.PROCESAR, listaExterno, listaLocal),
    )

    var indexAcutal = mutableStateOf(0)
}
