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
        TabEstadoLista(impuesto.nombreListaExterna, TabTipo.PLANILLA, listaExterno),
        TabEstadoLista(impuesto.nombreListaLocal, TabTipo.PLANILLA, listaLocal),
        TabProcesarEstado(
            TabTipo.PROCESAR,
            listaExterno,
            listaLocal,
            impuesto.nombreProcesar,
            impuesto.nombreListaExterna,
            impuesto.nombreListaExterna
        ),
    )
    val tituloImpuesto = impuesto.titulo

    var indexAcutal = mutableStateOf(0)
}
