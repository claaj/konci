package com.github.claaj.konci.ui.conciliar.tabs.lista

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.claaj.konci.ui.conciliar.tabs.TabEstado
import com.github.claaj.konci.ui.conciliar.tabs.TabTipo
import java.nio.file.Path

@Stable
data class TabEstadoLista(
    override val nombre: String,
    override val tipo: TabTipo,
    val items: SnapshotStateList<Path>
) : TabEstado
