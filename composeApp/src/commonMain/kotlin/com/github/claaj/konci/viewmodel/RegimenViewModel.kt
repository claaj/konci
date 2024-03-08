package com.github.claaj.konci.viewmodel

import com.github.claaj.konci.enums.Impuestos
import com.github.claaj.konci.enums.Impuestos.Companion.filterByRegimen
import com.github.claaj.konci.enums.Regimen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class RegimenViewModel(regimen: Regimen): ViewModel() {

    val vmImpuestos = mutableListOf<ImpuestoViewModel>()

    init {
        val impuestosList = filterByRegimen(regimen)
        for (impuesto in impuestosList) {
            val vm = ImpuestoViewModel(impuesto, regimen)
            vmImpuestos.add(vm)
        }
    }

    private var _currentImpuesto = MutableStateFlow(vmImpuestos.first())
    var currentImpuesto = _currentImpuesto.asStateFlow()
    fun setCurrentImpuesto(vm: ImpuestoViewModel) {
        _currentImpuesto.value = vm
    }

    fun impuestosList(): List<Impuestos> {
        val list = mutableListOf<Impuestos>()
        for (vm in vmImpuestos) {
            list.add(vm.impuesto)
        }
        return list.toList()
    }
}