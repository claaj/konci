package ui.conciliar.tabs.lista

import androidx.compose.runtime.snapshots.SnapshotStateList
import ui.conciliar.tabs.TabEstado
import ui.conciliar.tabs.TabTipo
import java.nio.file.Path

data class TabEstadoLista(
    override val nombre: String,
    override val tipo: TabTipo,
    val items: SnapshotStateList<Path>
) : TabEstado
