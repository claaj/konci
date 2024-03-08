package com.github.claaj.konci.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.claaj.konci.enums.NavDestination
import moe.tlaster.precompose.navigation.Navigator
import com.github.claaj.konci.ui.components.AppLogo
import com.github.claaj.konci.viewmodel.MainViewModel


@Composable
fun MainRail(vm: MainViewModel, navigator: Navigator) {
    NavigationRail(
        modifier = Modifier.width(100.dp),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
    ) {
        AppLogo(64.dp)
        NavDestination.entries.forEach {
            val selected = it.route == vm.currentRoute.collectAsState().value
            if (it == NavDestination.About) Spacer(Modifier.weight(1f))
            NavigationRailItem(
                icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
                label = { Text(it.title) },
                onClick = { if(!selected) navigator.navigate(it.route) },
                selected = selected,
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}
