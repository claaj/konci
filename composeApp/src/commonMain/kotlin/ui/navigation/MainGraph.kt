package ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import enums.NavDestination
import enums.Regimen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import ui.screen.About
import ui.screen.Conciliar
import viewmodel.RegimenViewModel
import viewmodel.MainViewModel

@Composable
fun MainGraph(vm: MainViewModel, navigator: Navigator) {
    val retencionesVm = RegimenViewModel(Regimen.Retenciones)
    val retencionesNavigator = Navigator()
    val percepcionesVm = RegimenViewModel(Regimen.Percepciones)
    val percepcionesNavigator = Navigator()

    Row {
        MainRail(vm, navigator)
        NavHost(navigator, initialRoute = NavDestination.Percepciones.route) {
            NavDestination.entries.forEach { destination ->
                scene(destination.route) {
                    when(destination) {
                        NavDestination.Percepciones -> Conciliar(percepcionesVm, percepcionesNavigator)
                        NavDestination.Retenciones -> Conciliar(retencionesVm, retencionesNavigator)
                        NavDestination.About -> About("1.0.0")
                    }
                    vm.setCurrentRoute(destination.route)
                }
            }
        }
    }
}