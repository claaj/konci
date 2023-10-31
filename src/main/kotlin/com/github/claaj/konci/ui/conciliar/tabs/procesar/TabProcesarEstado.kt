package com.github.claaj.konci.ui.conciliar.tabs.procesar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.claaj.konci.ui.conciliar.tabs.TabEstado
import com.github.claaj.konci.ui.conciliar.tabs.TabTipo
import java.nio.file.Path

@Stable
data class TabProcesarEstado(
    override val tipo: TabTipo,
    var listaExterno: SnapshotStateList<Path>,
    var listaLocal: SnapshotStateList<Path>,
    override val nombre: String,
    val origenExterno: String,
    val origenLocal: String,
) : TabEstado {
    var rutaGuardado: String? by mutableStateOf(null)
}