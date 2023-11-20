package com.github.claaj.konci.ui.conciliar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.claaj.konci.ui.conciliar.tabs.TabsConciliarEstado

@Stable
data class SeccionConciliarEstado(
    val regimen: Regimen
) {
    var indexActual by mutableStateOf(0)
    val impuestosConciliar: MutableList<TabsConciliarEstado> = mutableListOf()
    val impuestos: MutableList<Impuesto> = mutableListOf()

    init {
        val impuestosTodos = Impuesto.entries.toMutableList()

        when (regimen) {
            Regimen.Retenciones -> {
                impuestosTodos.removeAll { impuestoRet ->
                    impuestoRet.regimen == Regimen.Percepciones
                }
            }

            Regimen.Percepciones -> {
                impuestosTodos.removeAll { impuestoPercep ->
                    impuestoPercep.regimen == Regimen.Retenciones
                }
            }

            Regimen.Ambos -> throw UnsupportedOperationException()
        }

        impuestosTodos.forEach { impuesto ->
            val conciliarImpuesto = TabsConciliarEstado(regimen, impuesto)
            impuestosConciliar.add(conciliarImpuesto.copy())
            impuestos.add(impuesto)
        }
    }
}
