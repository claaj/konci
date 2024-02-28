package viewmodel

import enums.NavDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class MainViewModel: ViewModel() {
    private var _currentRoute = MutableStateFlow(NavDestination.Percepciones.route)
    val currentRoute = _currentRoute.asStateFlow()
    fun setCurrentRoute(route: String) {
        _currentRoute.value = route
    }
}