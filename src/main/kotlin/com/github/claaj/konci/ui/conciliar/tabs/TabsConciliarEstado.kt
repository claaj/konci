package com.github.claaj.konci.ui.conciliar.tabs

import androidx.compose.runtime.*
import com.github.claaj.konci.ui.conciliar.Impuestos
import com.github.claaj.konci.ui.conciliar.tabs.lista.TabEstadoLista
import com.github.claaj.konci.ui.conciliar.tabs.procesar.TabProcesarEstado
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

    var indexAcutal by mutableStateOf(0)
}