package ui.conciliar.tabs.procesar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ui.conciliar.tabs.TabEstado
import ui.conciliar.tabs.TabTipo
import java.nio.file.Path

data class TabEstadoProcesar(
    override val nombre: String,
    override val tipo: TabTipo,
    var listaExterno: SnapshotStateList<Path>,
    var listaLocal: SnapshotStateList<Path>,
) : TabEstado {
    var rutaGuardado = mutableStateOf("")
}
