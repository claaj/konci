package ui.conciliar.tabs

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import ui.conciliar.Impuestos
import ui.conciliar.tabs.lista.TabEstadoLista
import ui.conciliar.tabs.procesar.TabProcesarEstado
import java.nio.file.Path

@Stable
data class TabsConciliarEstado(
    val impuesto: Impuestos,
) {
    private var listaExterno = mutableStateListOf<Path>()
    private var listaLocal = mutableStateListOf<Path>()
    val items: List<TabEstado> = listOf(
        TabEstadoLista(impuesto.nombrePrimerTab, TabTipo.PLANILLA, listaExterno),
        TabEstadoLista(impuesto.nombreSegundoTab, TabTipo.PLANILLA, listaLocal),
        TabProcesarEstado(impuesto.nombreTercerTab, TabTipo.PROCESAR, listaExterno, listaLocal),
    )
    val tituloImpuesto = impuesto.titulo

    var indexAcutal = mutableStateOf(0)
}
